package com.yao.express.service.user.api.manager;

import com.yao.express.service.user.response.AppResponse;
import com.github.pagehelper.PageInfo;
import com.yao.express.service.user.dto.ListQueryOption;
import com.yao.express.service.user.entity.Driver;
import com.yao.express.service.user.entity.DriverCreateRequest;
import com.yao.express.service.user.services.DriverService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("managers/drivers")
public class DriverMngController {

    @Resource
    private DriverService driverService;

    @GetMapping
    public AppResponse<PageInfo<Driver>> getDrivers(@RequestParam(required = false) Integer page,
                                                    @RequestParam(required = false) Integer size,
                                                    @RequestParam(required = false) List<String> between,
                                                    // 如"+id,-createTime"
                                                    @RequestParam(required = false) String sorts,
                                                    @RequestParam(required = false) String keyword,
                                                    @RequestParam(required = false) Boolean hasVehicle) {

        ListQueryOption listQueryOption = ListQueryOption.build()
                .page(page).size(size)
                .between(between)
                .sorts(sorts)
                .search(keyword, Arrays.asList("driver_id", "name", "phone"));

        if (null != hasVehicle) {
            if (hasVehicle) {
                listQueryOption.isNotNull("vehicle_id");
            } else {
                listQueryOption.isNull("vehicle_id");
            }
        }

        return driverService.getManagerDriverList(listQueryOption);
    }

    @PostMapping
    public AppResponse<Driver> createDriver(@RequestBody DriverCreateRequest driverCreateRequest) {
        return driverService.createDriver(driverCreateRequest);
    }

    @PutMapping("/{driverId}")
    public AppResponse<Driver> updateDriver(@PathVariable String driverId,
                                            @RequestBody Driver driver) {
        return driverService.updateDriver(driver);
    }
}
