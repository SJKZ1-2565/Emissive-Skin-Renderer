package com.sjkz1.emissive_skin_renderer.utils.enums;

public class SkinPart {
    public enum Part {
        //TODO Add all skin part
        HEAD(0, 0, 31, 15),
        HAT(32, 0, 63, 15);

        private final int maxUvX;
        private final int minUvX;

        public int getMinUvX() {
            return minUvX;
        }


        public int getMinUvY() {
            return minUvY;
        }


        private final int minUvY;

        public int getMaxUvX() {
            return maxUvX;
        }


        public int getMaxUvY() {
            return maxUvY;
        }


        private final int maxUvY;


        Part(int minUvX, int minUvY, int maxUvX, int maxUvY) {
            this.maxUvX = maxUvX;
            this.maxUvY = maxUvY;
            this.minUvX = minUvX;
            this.minUvY = minUvY;
        }
    }
}
