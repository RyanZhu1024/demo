package com.example.controllers.wrappers;

import lombok.Data;

/**
 * Created by RyanZhu on 4/7/16.
 */
@Data
public abstract class CollectionRes {
    private String nextPageToken;
    private Integer resultSizeEstimate;
}
