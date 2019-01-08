package com.zssn.service;

import com.zssn.exceptions.UnprocessableEntityApiException;
import com.zssn.model.entity.Inventory;
import com.zssn.model.entity.Survivor;
import com.zssn.model.enumeration.Resource;
import com.zssn.model.repository.SurvivorRepository;
import com.zssn.util.ResourceUtils;
import com.zssn.vo.ResourceVO;
import com.zssn.vo.TradeSurvivorVO;
import com.zssn.vo.TradeVO;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class TradeService {

    @Autowired
    private SurvivorService survivorService;

    @Autowired
    private SurvivorRepository survivorRepository;

    @Autowired
    private ResourceUtils resourceUtils;

    @Transactional
    public TradeVO trade(TradeVO tradeVO) {
        final TradeSurvivorVO giver = tradeVO.getGiver();
        final TradeSurvivorVO receiver = tradeVO.getReceiver();

        resourceUtils.stackEqualResources(giver);
        resourceUtils.stackEqualResources(receiver);

        validateSurvivor(giver, receiver);
        validateResourcePoints(giver, receiver);

        final Survivor giverDB = survivorService.findById(giver.getId());
        final Survivor receiverDB = survivorService.findById(receiver.getId());

        validateSurvivorInfection(giverDB);
        validateSurvivorInfection(receiverDB);

        validateSurvivorResources(giverDB, giver.getResources());
        validateSurvivorResources(receiverDB, receiver.getResources());

        addResource(giverDB, receiver.getResources());
        addResource(receiverDB, giver.getResources());

        removeResource(giverDB, giver.getResources());
        removeResource(receiverDB, receiver.getResources());

        survivorRepository.save(giverDB);
        survivorRepository.save(receiverDB);

        return createTradeVO(giverDB, receiverDB);
    }

    private TradeVO createTradeVO(Survivor giverDB, Survivor receiverDB) {
        return TradeVO.builder()
            .giver(TradeSurvivorVO.builder()
                .id(giverDB.getId())
                .resources(createResourceVO(giverDB.getInventory()))
                .build())
            .receiver(TradeSurvivorVO.builder()
                .id(receiverDB.getId())
                .resources(createResourceVO(receiverDB.getInventory()))
                .build())
            .build();
    }

    private List<ResourceVO> createResourceVO(List<Inventory> inventory) {
        final List<ResourceVO> resources = new ArrayList<>();
        inventory.forEach((i) -> {
            resources.add(ResourceVO.builder().resource(i.getResource()).quantity(i.getQuantity()).build());
        });

        return resources;
    }

    private void validateSurvivorInfection(Survivor survivor) {
        if (survivor.isInfected()) {
            log.warn("Infected survivors can not trade. survivorId={}", survivor.getId());
            throw new UnprocessableEntityApiException("unprocessableentity.infected.survivor.cant.trade");
        }
    }

    private void removeResource(Survivor survivor, List<ResourceVO> resources) {
        resources.forEach((res) -> {
            final Inventory inventory = getInventoryByResource(survivor.getInventory(), res.getResource());
            inventory.setQuantity(inventory.getQuantity() - res.getQuantity());
        });

        survivor.getInventory().removeIf(res -> res.getQuantity() == 0);
    }

    private void addResource(Survivor survivor, List<ResourceVO> resources) {
        resources.forEach((res) -> {
            final Inventory inventory = getInventoryByResource(survivor.getInventory(), res.getResource());
            if (inventory == null) {
                survivor.getInventory().add(Inventory.builder().resource(res.getResource()).quantity(res.getQuantity()).survivor(survivor).build());
            } else {
                inventory.setQuantity(inventory.getQuantity() + res.getQuantity());
            }
        });
    }

    private void validateSurvivorResources(Survivor survivor, List<ResourceVO> resources) {
        for (ResourceVO res : resources) {
            final Inventory resource = getInventoryByResource(survivor.getInventory(), res.getResource());
            if (resource == null) {
                log.warn("Survivor do not have requested resource. survivorId={}, resource={}, quantity={}", survivor.getId(), res.getResource(),
                    res.getQuantity());
                throw new UnprocessableEntityApiException("unprocessableentity.survivor.dont.have.requested.resource");
            }

            if (res.getQuantity() > resource.getQuantity()) {
                log.warn(
                    "Survivor does not have requested amount of resource. survivorId={}, resource={}, requestedQuantity={}, availableQuantity={}",
                    survivor.getId(), res.getResource(), res.getQuantity(), resource.getQuantity());
                throw new UnprocessableEntityApiException("unprocessableentity.invalid.resource.amount");
            }
        }
    }

    private Inventory getInventoryByResource(List<Inventory> inventories, Resource resource) {
        for (Inventory inventory : inventories) {
            if (inventory.getResource() == resource) {
                return inventory;
            }
        }

        return null;
    }

    private void validateSurvivor(TradeSurvivorVO giver, TradeSurvivorVO receiver) {
        if (giver.getId() == receiver.getId()) {
            log.warn("Attempt to trade with himself. giverId={}, receiverId={}", giver.getId(), receiver.getId());
            throw new UnprocessableEntityApiException("unprocessableentity.giver.and.receiver.cant.be.equal");
        }
    }

    private void validateResourcePoints(TradeSurvivorVO giver, TradeSurvivorVO receiver) {
        int giverPoints = 0;
        int receiverPoints = 0;

        for (ResourceVO res : giver.getResources()) {
            giverPoints += res.getQuantity() * res.getResource().getPoints();
        }

        for (ResourceVO rec : receiver.getResources()) {
            receiverPoints += rec.getQuantity() * rec.getResource().getPoints();
        }

        if (giverPoints != receiverPoints) {
            log.warn("Attempt to do a invalid trade. Giver and receiver points are different. giverPoints={}, receiverPoints={}");
            throw new UnprocessableEntityApiException("unprocessableentity.points.count.not.equal");
        }
    }
}
