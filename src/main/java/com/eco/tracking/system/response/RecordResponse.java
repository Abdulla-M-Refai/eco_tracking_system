package com.eco.tracking.system.response;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecordResponse <T>
{
    private T data;
}
