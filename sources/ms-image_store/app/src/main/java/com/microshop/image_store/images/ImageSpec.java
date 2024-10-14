package com.microshop.image_store.images;

/**
 * All the images are squares! The specification is just few restrictions for what could be the
 * right output/input.
 */
public class ImageSpec {
    public static enum Size {
        EXTRA_SMALL(128),
        SMALL(256),
        MEDIUM(512),
        BIG(1024),
        EXTRA_BIG(2048);

        private int width;

        /**
         * @param pixel_width - the width x of the image. All the images are squares.
         */
        private Size(int pixel_width) {
            this.width = pixel_width;
        }

        public int getWidth() {
            return width;
        }
    }

    // public static enum Quality{
    //    Q10(10),
    //    Q20(20),
    //    Q30(30),
    //    Q40(40),
    //    Q50(50),
    //    Q60(60),
    //    Q70(70),
    //    Q80(80),
    //    Q90(90),
    //    Q100(100);

    //    private int quality;

    //    private Quality(int quality){
    //        this.quality = quality;
    //    }
    // }

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
                    "quality should be between 1 (inclusive) and 100 (inclusive). given = "
                            + quality);
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
