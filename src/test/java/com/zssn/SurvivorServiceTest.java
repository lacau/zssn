package com.zssn;

import com.zssn.exceptions.NotFoundApiException;
import com.zssn.model.entity.Inventory;
import com.zssn.model.entity.Location;
import com.zssn.model.entity.Survivor;
import com.zssn.model.enumeration.Gender;
import com.zssn.model.enumeration.Resource;
import com.zssn.model.repository.SurvivorRepository;
import com.zssn.service.SurvivorService;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SurvivorServiceTest {

    private static final String NAME = "Lacau";

    private static final Integer AGE = 32;

    private static final int TOTAL_AMMUNITION = 17;

    private static final int TOTAL_FOOD = 3;

    private static final int TOTAL_MEDICATION = 5;

    private static final int TOTAL_WATER = 19;

    @InjectMocks
    private SurvivorService survivorService;

    @Mock
    private SurvivorRepository survivorRepository;

    @Test
    public void testCreateWhenShouldStackEqualResources() {
        Mockito.when(survivorRepository.save(Mockito.any(Survivor.class))).thenAnswer(i -> i.getArguments()[0]);

        final Survivor survivor = survivorService.create(createSurvivorWithInventory());
        Mockito.verify(survivorRepository, Mockito.times(1)).save(Mockito.any(Survivor.class));
        Assert.assertEquals(survivor.getName(), NAME);
        Assert.assertEquals(survivor.getAge(), AGE);
        Assert.assertEquals(survivor.getGender(), Gender.MALE);
        Assert.assertFalse(survivor.isInfected());

        Assert.assertEquals(survivor.getInventory().size(), 4);
        Assert.assertEquals(getQuantityByResource(survivor.getInventory(), Resource.AMMUNITION), TOTAL_AMMUNITION);
        Assert.assertEquals(getQuantityByResource(survivor.getInventory(), Resource.FOOD), TOTAL_FOOD);
        Assert.assertEquals(getQuantityByResource(survivor.getInventory(), Resource.MEDICATION), TOTAL_MEDICATION);
        Assert.assertEquals(getQuantityByResource(survivor.getInventory(), Resource.WATER), TOTAL_WATER);
    }

    @Test
    public void testMarkAsInfected() {
        Mockito.when(survivorRepository.save(Mockito.any(Survivor.class))).thenReturn(null);

        final Survivor survivor = createSurvivorWithInventory();
        survivorService.markAsInfected(survivor);
        Mockito.verify(survivorRepository, Mockito.times(1)).save(Mockito.any(Survivor.class));
        Assert.assertEquals(survivor.getName(), NAME);
        Assert.assertEquals(survivor.getAge(), AGE);
        Assert.assertEquals(survivor.getGender(), Gender.MALE);
        Assert.assertTrue(survivor.isInfected());
    }

    @Test(expected = NotFoundApiException.class)
    public void testFindByIdWhenSurvivorNotFound() {
        Mockito.when(survivorRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        survivorService.findById(1L);
    }

    @Test
    public void testFindByIdWhenSurvivorFound() {
        final Survivor survivor = createSurvivorWithInventory();
        Mockito.when(survivorRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(survivor));

        survivorService.findById(1L);
    }

    private Survivor createSurvivorWithInventory() {
        final List<Inventory> inventory = new ArrayList<>();
        inventory.add(Inventory.builder().resource(Resource.AMMUNITION).quantity(10).build());
        inventory.add(Inventory.builder().resource(Resource.FOOD).quantity(3).build());
        inventory.add(Inventory.builder().resource(Resource.MEDICATION).quantity(2).build());
        inventory.add(Inventory.builder().resource(Resource.WATER).quantity(15).build());
        inventory.add(Inventory.builder().resource(Resource.WATER).quantity(4).build());
        inventory.add(Inventory.builder().resource(Resource.AMMUNITION).quantity(7).build());
        inventory.add(Inventory.builder().resource(Resource.MEDICATION).quantity(3).build());

        return Survivor.builder()
            .name(NAME)
            .age(AGE)
            .gender(Gender.MALE)
            .inventory(inventory)
            .location(Location.builder().longitude(BigDecimal.ONE).latitude(BigDecimal.TEN).build())
            .build();
    }

    private int getQuantityByResource(List<Inventory> inventory, Resource resource) {
        for (Inventory i : inventory) {
            if (i.getResource().equals(resource)) {
                return i.getQuantity();
            }
        }

        return 0;
    }
}
