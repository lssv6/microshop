package com.microshop.image_store.images;

/**
 * All the images are squares! The specification is just few restrictions for what could be the
 * right output/input.
 */
public class ImageSpec {
    public static enum Size {
        EXTRA_SMALL(128, "xs"),
        SMALL(256, "s"),
        MEDIUM(512, "m"),
        LARGE(1024, "l"),
        EXTRA_LARGE(2048, "xl");

        private int width;
        private String abbreviation;

        /**
         * @param pixel_width - the width x of the image. All the images are squares.
         */
        private Size(int pixel_width, String abbreviation) {
            this.width = pixel_width;
            this.abbreviation = abbreviation;
        }

        public int getWidth() {
            return width;
        }

        public String getAbbreviation() {
            return abbreviation;
        }

        public static Size fromWidth(int width) {
            return switch (width) {
                case 128 -> EXTRA_SMALL;
                case 256 -> SMALL;
                case 512 -> MEDIUM;
                case 1024 -> LARGE;
                case 2048 -> EXTRA_LARGE;
                default -> throw new IllegalArgumentException("Cannot find a suitable Size for " + width);
            };
        }

        public static Size fromString(String string) {
            return switch (string.toUpperCase()) {
                case "XS", "EXTRA_SMALL" -> EXTRA_SMALL;
                case "S", "SMALL" -> SMALL;
                case "M", "MEDIUM" -> MEDIUM;
                case "L", "LARGE" -> LARGE;
                case "XL", "EXTRA_LARGE" -> EXTRA_LARGE;
                default -> throw new IllegalArgumentException("Cannot find a suitable Size for " + string);
            };
        }
    }

    private Size size;
    private int quality;

    /***
     * A image must have specified width and quality.
     * @param width - must be a power of two
     * @param quality - is a number between 0 and 100
     */
    public ImageSpec(Size size, int quality) {
        this.size = size;
        if (quality <= 0 || quality > 100) {
            throw new IllegalArgumentException(
                    "quality should be between 1 (inclusive) and 100 (inclusive). given = " + quality);
        }
        this.quality = quality;
    }

    public Size getSize() {
        return size;
    }

    public int getQuality() {
        return quality;
    }
}
