package com.smart.gestion_colis.common;

import java.util.ArrayList;
import java.util.List;

public class Constants {
    public static final Long AC_USER = 1L;
    public static final Long DAC_USER = 0L;

    public static final List<Long> USERS_ACTIV = new ArrayList<Long>() {{
        add(AC_USER);
        add(DAC_USER);
    }};
}