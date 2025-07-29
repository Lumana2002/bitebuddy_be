//package com.rajan.foodDeliveryApp;
//
//import com.rajan.foodDeliveryApp.domain.dto.FoodDto;
//import com.rajan.foodDeliveryApp.domain.dto.MenuDto;
//import com.rajan.foodDeliveryApp.domain.dto.RestaurantDto;
//import com.rajan.foodDeliveryApp.domain.entities.RestaurantEntity;
//
//import java.math.BigDecimal;
//
//public final class TestDataUtil {
//
//    private TestDataUtil() {
//
//    }
//
//    public static RestaurantDto createTestRestaurantDtoA() {
//        return RestaurantDto.builder()
//                .name("Ram Momo")
//                .cuisine("Nepali")
//                .build();
//    }
//
//    public static RestaurantDto createTestRestaurantDtoB() {
//        return RestaurantDto.builder()
//                .name("Gourmet Eatery")
//                .cuisine("Burger")
//                .build();
//    }
//
//    public static RestaurantEntity createTestRestaurantA() {
//        return RestaurantEntity.builder()
//                .name("Ram Momo")
//                .cuisine("Nepali")
//                .build();
//    }
//
//    public static RestaurantEntity createTestRestaurantB() {
//        return RestaurantEntity.builder()
//                .name("Gourmet Eatery")
//                .cuisine("Burger")
//                .build();
//    }
//
//    // Food Test Util
//    public static FoodDto createTestFoodDtoA(){
//        return FoodDto.builder()
//                .foodId(201L)
//                .name("Buff Momo")
//                .category("Momo")
//                .price(BigDecimal.valueOf(200))
//                .build();
//    }
//
////    public static MenuDto createTestMenuDtoA(){
////        return MenuDto.builder()
////                .menuId(10)
////                .name("Breakfast Menu")
////                .restaurant()
////                .foods()
////                .build();
////    }
//}
