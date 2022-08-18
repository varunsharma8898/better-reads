package com.varun.selfstudy.util;

import org.springframework.util.StringUtils;

public class Util {

    public static final String COVER_IMG_ROOT = "https://covers.openlibrary.org/b/id/";

    private static final String NO_IMG_AVAILABLE = "../images/No_image_available.png";

    private Util() {
    }

    public static String getImageUrlForCoverId(String coverId, ImageSize imageSize) {
        StringBuilder sb = new StringBuilder();
        if (StringUtils.hasText(coverId)) {
            sb.append(COVER_IMG_ROOT).append(coverId).append("-").append(imageSize.getSize()).append(".jpg");
            return sb.toString();
        }
        return NO_IMG_AVAILABLE;
    }

}
