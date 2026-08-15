package net.lj1o1.foodplus.item;

import net.lj1o1.foodplus.Config;
import net.lj1o1.foodplus.FoodPlus;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS =
            DeferredRegister.createItems(FoodPlus.MODID);

    static int nut = FoodPlus.nutrition;
    static float sat = FoodPlus.saturation;



    //Registers the Item
    public static final DeferredItem<Item> KNIFE = ITEMS.register("knife",() -> new Item(new Item.Properties().stacksTo(1)));

    //Food Items

    //Ingredients
    public static final DeferredItem<Item> SALT = ITEMS.register("salt",() -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> FLOUR = ITEMS.register("flour",() -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> FILTER = ITEMS.register("filter",() -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> BUTTER = ITEMS.register("butter",() -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(1).saturationModifier(0.2f).build())));


    // Breads
    public static final DeferredItem<Item> BREAD_SLICE = ITEMS.register("bread_slice", () -> new Item((new Item.Properties().food(new FoodProperties.Builder().nutrition(1)
            .saturationModifier(0.6f).build()))));
    public static final DeferredItem<Item> TOAST = ITEMS.register("toast", () -> new Item((new Item.Properties().food(new FoodProperties.Builder().nutrition(3)
            .saturationModifier(0.2f).build()))));
    public static final DeferredItem<Item> BUTTERED_BREAD = ITEMS.register("buttered_bread", () -> new Item((new Item.Properties()
            .food(new FoodProperties.Builder().nutrition(4).saturationModifier(1.2f).build()))));


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);

    }



}




