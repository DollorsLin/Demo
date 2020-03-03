package com.yun.service;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.yun.mapper.ProductMapper;
import com.yun.pojo.*;
import com.yun.repository.ProductRepository;
import com.yun.utils.Content;
import com.yun.utils.PageUtils;
import com.yun.vo.ProductVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.github.pagehelper.page.PageMethod.startPage;

/**
 * 产品表
 */
@Service
@Slf4j
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ScoreService scoreService;

    @Autowired
    private JPService jpService;

    @Autowired
    private SerialNumberService serialNumberService;

    @Autowired
    private CommandService commandService;

    @Autowired
    private HeartbeatLogService heartbeatLogService;

    public Product save(Product product) {
        Product save = productRepository.save(product);
        Integer lotteryPerCoinCloud = product.getLotteryPerCoinCloud();
        BigDecimal percentageCloud = product.getPercentageCloud();
        Integer jpMaxCloud = product.getJpMaxCloud();
        Integer productId = save.getId();
        List<SerialNumber> list = serialNumberService.findByProductId(productId);
        if (list.size() > 0) {
            if (lotteryPerCoinCloud != null || percentageCloud != null || jpMaxCloud != null) {
                Command command = new Command();
                command.setAddTime(new Date());
                command.setProductId(productId);
                command.setCommand(100);
                command.setIsDeleted(0);
                Map<String, String> map = new HashMap<>();
                map.put("m_LotteryRatio", String.valueOf(lotteryPerCoinCloud));
                map.put("m_ExtractRatio", String.valueOf(percentageCloud));
                map.put("m_MaxJpLimit", String.valueOf(jpMaxCloud));
                String params = JSON.toJSONString(map);
                command.setParameters(params);
                command.setSerialNumber(list.get(0).getSerialNumber());
                commandService.add(command);
            }
        }
        return save;
    }

    public void add(Product product) {
        productRepository.save(product);
    }

    public void update(Product product) {
//        Integer productId = product.getId();
//        Product pro = get(productId);
//        Integer lotteryPerCoinCloud = product.getLotteryPerCoinCloud();
//        if (lotteryPerCoinCloud == null) {
//            lotteryPerCoinCloud = 0;
//        }
//        Integer jpMaxCloud = product.getJpMaxCloud();
//        if (jpMaxCloud == null) {
//            jpMaxCloud = 0;
//        }
//        BigDecimal percentageCloud = product.getPercentageCloud();
//        if (percentageCloud == null) {
//            percentageCloud = new BigDecimal(0);
//        }
//        List<SerialNumber> list = serialNumberService.findByProductId(productId);
//        if (list.size() > 0) {
//            Integer lottery = pro.getLotteryPerCoinCloud();
//            if (lottery == null) {
//                lottery = 0;
//            }
//            BigDecimal percent = pro.getPercentageCloud();
//            if (percent == null) {
//                percent = new BigDecimal(0);
//            }
//            Integer jpMax = pro.getJpMaxCloud();
//            if (jpMax == null) {
//                jpMax = 0;
//            }
//            if (!lotteryPerCoinCloud.equals(lottery)
//                    || !percentageCloud.equals(percent)
//                    || !jpMaxCloud.equals(jpMax)) {
//                Command command = new Command();
//                command.setProductId(productId);
//                command.setAddTime(new Date());
//                command.setCommand(100);
//                command.setIsDeleted(0);
//                Map<String, String> map = new HashMap<>();
//                map.put("m_LotteryRatio", String.valueOf(lotteryPerCoinCloud));
//                map.put("m_MaxJpLimit", String.valueOf(jpMaxCloud));
//                map.put("m_ExtractRatio", String.valueOf(percentageCloud));
//                String params = JSON.toJSONString(map);
//                command.setParameters(params);
//                command.setSerialNumber(list.get(0).getSerialNumber());
//                commandService.add(command);
//
//            }
//        }
        productRepository.save(product);
    }


    public Product get(Integer id) {
        return productRepository.findProductById(id);
    }


    public PageUtils search(@RequestParam Map<String, Object> params) {
        Integer page = Integer.parseInt(params.get("page").toString());
        Integer limit = Integer.parseInt(params.get("limit").toString());
        Page<ProductVO> result = startPage(page, limit);
        productMapper.search(params);
        PageUtils pageUtils = new PageUtils(result);
        return pageUtils;

    }


    public Integer findMax(@RequestParam Map<String, Object> params) {

        return productMapper.max(params);

    }

    public void deleteByTime(Map<String, Object> params) {
        scoreService.deleteByTime(params);
        jpService.deleteByTime(params);
    }


    public void setting(Product product) {
        Integer productId = product.getId();
        Product pro = get(productId);
        Integer lotteryPerCoinCloud = product.getLotteryPerCoinCloud();
        if (lotteryPerCoinCloud == null) {
            lotteryPerCoinCloud = 0;
        }
        Integer jpMaxCloud = product.getJpMaxCloud();
        if (jpMaxCloud == null) {
            jpMaxCloud = 0;
        }
        BigDecimal percentageCloud = product.getPercentageCloud();
        if (percentageCloud == null) {
            percentageCloud = new BigDecimal(0);
        }


        //判断抽水系数
        if (percentageCloud.compareTo(new BigDecimal(1)) > 0) {
            log.info("设置参数有误");
            return;
        }



        List<SerialNumber> list = serialNumberService.findByProductId(productId);
        if (list.size() > 0) {
            Integer lottery = pro.getLotteryPerCoinCloud();
            if (lottery == null) {
                lottery = 0;
            }
            BigDecimal percent = pro.getPercentageCloud();
            if (percent == null) {
                percent = new BigDecimal(0);
            }
            Integer jpMax = pro.getJpMaxCloud();
            if (jpMax == null) {
                jpMax = 0;
            }
            if (!lotteryPerCoinCloud.equals(lottery)
                    || !percentageCloud.equals(percent)
                    || !jpMaxCloud.equals(jpMax)) {
                Command command = new Command();
                command.setProductId(productId);
                command.setAddTime(new Date());
                command.setCommand(100);
                command.setIsDeleted(0);
                Map<String, String> map = new HashMap<>();
                map.put("m_LotteryRatio", String.valueOf(lotteryPerCoinCloud));
                map.put("m_MaxJpLimit", String.valueOf(jpMaxCloud));
                map.put("m_ExtractRatio", String.valueOf(percentageCloud));
//                map.put("type","0");
                String params = JSON.toJSONString(map);
                command.setParameters(params);
                command.setSerialNumber(list.get(0).getSerialNumber());
                commandService.add(command);
            }
        }
        productRepository.save(product);
    }

    public List<Product> findByPlaceId(Integer placeId) {
        return productRepository.findAllByPlaceId(placeId);
    }

    public void checkOnline() {
        Date time = new Date();
        long now = time.getTime();
        List<Product> list = productRepository.findAllByIsOnline(1);

        for (Product product : list) {
            System.out.println("productId:"+product.getId());
            Integer productId = product.getId();
            HeartbeatLog heartbeatLog = heartbeatLogService.findLastByProductId(productId);
            if (heartbeatLog == null) {
                product.setIsOnline(0);
                productRepository.save(product);
                continue;
            }
            Date addTime = heartbeatLog.getAddTime();
            System.out.println("lastTime:" + addTime);
            long lastTime = addTime.getTime();
            //大于表示离线
            if (now - lastTime > Content.OVER_TIME * Content.OVER_TIME_COUNT) {
                product.setIsOnline(0);
                productRepository.save(product);
            }
        }
    }
}

