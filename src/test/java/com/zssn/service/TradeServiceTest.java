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
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TradeServiceTest {

    @InjectMocks
    private TradeService tradeService;

    @Mock
    private SurvivorService survivorService;

    @Mock
    private SurvivorRepository survivorRepository;

    @Spy
    private ResourceUtils resourceUtils;

    @Test(expected = UnprocessableEntityApiException.class)
    public void testTradeWhenSurvivorIsEqual() {
        tradeService.trade(createTradeVO(1L, 1L));
    }

    @Test(expected = UnprocessableEntityApiException.class)
    public void testTradeWhenDifferentPoints() {
        final TradeVO tradeVO = createTradeVO(1L, 2L);
        tradeVO.getGiver().getResources().add(createResourceVO(Resource.WATER, 2));
        tradeVO.getReceiver().getResources().add(createResourceVO(Resource.WATER, 1));

        tradeService.trade(tradeVO);
    }

    @Test(expected = UnprocessableEntityApiException.class)
    public void testTradeWhenSurvivorIsInfected() {
        final TradeVO tradeVO = createTradeVO(1L, 2L);
        tradeVO.getGiver().getResources().add(createResourceVO(Resource.WATER, 1));
        tradeVO.getReceiver().getResources().add(createResourceVO(Resource.AMMUNITION, 1));
        tradeVO.getReceiver().getResources().add(createResourceVO(Resource.FOOD, 1));

        Mockito.when(survivorService.findById(Mockito.anyLong())).thenReturn(Survivor.builder().infected(true).build());

        tradeService.trade(tradeVO);
    }

    @Test(expected = UnprocessableEntityApiException.class)
    public void testTradeWhenSurvivorDontHaveRequestedResource() {
        final TradeVO tradeVO = createTradeVO(1L, 2L);
        tradeVO.getGiver().getResources().add(ResourceVO.builder().resource(Resource.WATER).quantity(1).build());
        tradeVO.getReceiver().getResources().add(createResourceVO(Resource.AMMUNITION, 1));
        tradeVO.getReceiver().getResources().add(createResourceVO(Resource.FOOD, 1));

        Mockito.when(survivorService.findById(Mockito.anyLong())).thenReturn(createSurvivor());

        tradeService.trade(tradeVO);
    }

    @Test(expected = UnprocessableEntityApiException.class)
    public void testTradeWhenSurvivorDontHaveRequestedResourceQuantity() {
        final TradeVO tradeVO = createTradeVO(1L, 2L);
        tradeVO.getGiver().getResources().add(ResourceVO.builder().resource(Resource.AMMUNITION).quantity(2).build());
        tradeVO.getReceiver().getResources().add(createResourceVO(Resource.AMMUNITION, 2));

        Mockito.when(survivorService.findById(Mockito.anyLong())).thenReturn(createSurvivor());

        tradeService.trade(tradeVO);
    }

    @Test
    public void testTradeWhenTradeOK() {
        final TradeVO tradeVO = createTradeVO(1L, 2L);
        tradeVO.getGiver().getResources().add(ResourceVO.builder().resource(Resource.FOOD).quantity(1).build());
        tradeVO.getReceiver().getResources().add(createResourceVO(Resource.AMMUNITION, 1));
        tradeVO.getReceiver().getResources().add(createResourceVO(Resource.MEDICATION, 1));

        Mockito.when(survivorService.findById(1L)).thenReturn(createSurvivor());
        Mockito.when(survivorService.findById(2L)).thenReturn(createSurvivor());
        Mockito.when(survivorRepository.save(Mockito.any(Survivor.class))).thenReturn(null);

        final TradeVO trade = tradeService.trade(tradeVO);
        Mockito.verify(survivorRepository, Mockito.times(2)).save(Mockito.any(Survivor.class));
        Assert.assertEquals(2, trade.getGiver().getResources().size());
        Assert.assertEquals(1, trade.getReceiver().getResources().size());
        Assert.assertTrue(trade.getGiver().getResources().contains(ResourceVO.builder().resource(Resource.AMMUNITION).quantity(2).build()));
        Assert.assertTrue(trade.getGiver().getResources().contains(ResourceVO.builder().resource(Resource.MEDICATION).quantity(2).build()));
        Assert.assertFalse(trade.getGiver().getResources().contains(ResourceVO.builder().resource(Resource.FOOD).quantity(1).build()));
        Assert.assertTrue(trade.getReceiver().getResources().contains(ResourceVO.builder().resource(Resource.FOOD).quantity(2).build()));
        Assert.assertFalse(trade.getReceiver().getResources().contains(ResourceVO.builder().resource(Resource.AMMUNITION).quantity(1).build()));
        Assert.assertFalse(trade.getReceiver().getResources().contains(ResourceVO.builder().resource(Resource.MEDICATION).quantity(1).build()));
    }

    private TradeVO createTradeVO(Long giverId, Long receiverId) {
        return TradeVO.builder()
            .giver(TradeSurvivorVO.builder()
                .resources(new ArrayList<>())
                .id(giverId)
                .build())
            .receiver(TradeSurvivorVO.builder()
                .resources(new ArrayList<>())
                .id(receiverId)
                .build())
            .build();
    }

    private ResourceVO createResourceVO(Resource resource, int quantity) {
        return ResourceVO.builder()
            .resource(resource)
            .quantity(quantity)
            .build();
    }

    private Survivor createSurvivor() {
        return Survivor.builder()
            .inventory(createInventory())
            .build();
    }

    private List<Inventory> createInventory() {
        final List<Inventory> inventory = new ArrayList<>();
        inventory.add(Inventory.builder().resource(Resource.AMMUNITION).quantity(1).build());
        inventory.add(Inventory.builder().resource(Resource.FOOD).quantity(1).build());
        inventory.add(Inventory.builder().resource(Resource.MEDICATION).quantity(1).build());

        return inventory;
    }
}
