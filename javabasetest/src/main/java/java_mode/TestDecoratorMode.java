/*
 * Fabric4cloud.com Inc.
 * Copyright (c) 2015-2020 All Rights Reserved.
 */
package java_mode;

import com.google.common.collect.Lists;

import java.util.List;

public class TestDecoratorMode {

    interface Drink{

        void make();
    }

    public static class GreenTea implements Drink{

        @Override
        public void make() {
            System.out.println("制作绿茶");
        }
    }

    public static class BlackTea implements Drink{

        @Override
        public void make() {
            System.out.println("制作红茶");
        }
    }

    public static class Coffee implements Drink{

        @Override
        public void make() {
            System.out.println("制作咖啡");
        }
    }

    static class DrinkDecorator implements Drink{

        private List<Material> materials;

        private Drink drink;

        public DrinkDecorator(List<Material> materials, Drink drink) {
            this.materials = materials;
            this.drink = drink;
        }

        @Override
        public void make() {
            drink.make();
            System.out.println("添加辅料:" + materials);
        }
    }

    static class Material{

        //辅料的名称
        private String name;
        //辅料的数量
        private Integer count;

        public Material(String name, Integer count) {
            this.name = name;
            this.count = count;
        }

        @Override
        public String toString() {
            return name + 'X' + count;
        }
    }

    public static void main(String[] args) {
        List<Material> materials1 = Lists.newArrayList(new Material("金桔柠檬", 1), new Material("珍珠", 1));
        DrinkDecorator mixDrink1 = new DrinkDecorator(materials1, new GreenTea());
        mixDrink1.make();

        List<Material> materials2 = Lists.newArrayList(new Material("芒果", 1), new Material("椰果", 1));
        DrinkDecorator mixDrink2 = new DrinkDecorator(materials2, new BlackTea());
        mixDrink2.make();
    }
}
