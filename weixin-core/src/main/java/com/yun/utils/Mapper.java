package com.yun.utils;

import java.util.List;

public interface Mapper <T>{
    List<T> list(String key, Integer customerId);

}
