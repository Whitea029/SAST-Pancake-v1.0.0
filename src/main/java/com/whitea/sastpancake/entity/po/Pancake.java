package com.whitea.sastpancake.entity.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pancake {

    private Integer id;

    private String title;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime createTime;

    private String ddl;

    private Integer isDone;

}
