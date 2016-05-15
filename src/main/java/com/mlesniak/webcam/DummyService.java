package com.mlesniak.webcam;

import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author Michael Lesniak (mlesniak@micromata.de)
 */
@Service
public class DummyService {
    public String time() {
        return new Date().toString();
    }
}
