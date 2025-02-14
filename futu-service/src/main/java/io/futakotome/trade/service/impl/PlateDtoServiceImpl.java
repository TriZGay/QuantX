package io.futakotome.trade.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.futakotome.trade.controller.vo.ListPlateRequest;
import io.futakotome.trade.controller.vo.ListPlateResponse;
import io.futakotome.trade.domain.code.MarketType;
import io.futakotome.trade.domain.code.PlateSetType;
import io.futakotome.trade.dto.PlateDto;
import io.futakotome.trade.mapper.PlateDtoMapper;
import io.futakotome.trade.service.PlateDtoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author pc
 * @description 针对表【t_plate】的数据库操作Service实现
 * @createDate 2023-04-11 10:12:42
 */
@Service
public class PlateDtoServiceImpl extends ServiceImpl<PlateDtoMapper, PlateDto>
        implements PlateDtoService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertBatch(List<PlateDto> newPlates) {
        List<PlateDto> allPlates = list();
        newPlates.removeIf(allPlates::contains);
        if (!newPlates.isEmpty()) {
            return getBaseMapper().insertBatch(newPlates);
        } else {
            return 0;
        }
    }

    @Override
    @Transactional
    public IPage<ListPlateResponse> page(ListPlateRequest request) {
        QueryWrapper<PlateDto> queryWrapper = Wrappers.query();
        Page<PlateDto> pagination = Page.of(1, 10);
        if (request.getName() != null) {
            queryWrapper.like("name", request.getName());
        }
        if (request.getMarket() != null) {
            queryWrapper.eq("market", request.getMarket());
        }
        if (request.getType() != null) {
            queryWrapper.eq("plate_type", request.getType());
        }
        if (request.getCurrent() != null) {
            pagination.setCurrent(request.getCurrent());
        }
        if (request.getSize() != null) {
            pagination.setSize(request.getSize());
        }
        return page(pagination, queryWrapper).convert(plateDto -> {
            ListPlateResponse response = new ListPlateResponse();
            response.setId(plateDto.getId());
            response.setName(plateDto.getName());
            response.setCode(plateDto.getCode());
            response.setMarket(MarketType.getNameByCode(plateDto.getMarket()));
            response.setMarketCode(plateDto.getMarket());
            response.setPlateType(PlateSetType.getNameByCode(plateDto.getPlateType()));
            response.setPlateTypeCode(plateDto.getPlateType());
            return response;
        });
    }
}




