package io.futakotome.trade.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.futakotome.trade.controller.vo.ListSubscribeRequest;
import io.futakotome.trade.controller.vo.ListSubscribeResponse;
import io.futakotome.trade.dto.SubDto;
import io.futakotome.trade.mapper.SubDtoMapper;
import io.futakotome.trade.service.SubDtoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author pc
 * @description 针对表【t_sub】的数据库操作Service实现
 * @createDate 2023-05-08 15:00:30
 */
@Service
public class SubDtoServiceImpl extends ServiceImpl<SubDtoMapper, SubDto>
        implements SubDtoService {

    @Override
    @Transactional
    public IPage<ListSubscribeResponse> findDetails(ListSubscribeRequest request) {
        QueryWrapper<SubDto> queryWrapper = Wrappers.query();
        Page<SubDto> pagination = Page.of(1, 10);
        if (request.getCurrent() != null) {
            pagination.setCurrent(request.getCurrent());
        }
        if (request.getSize() != null) {
            pagination.setSize(request.getSize());
        }
        return getBaseMapper().selectPage(pagination, queryWrapper).convert(dto -> {
            ListSubscribeResponse response = new ListSubscribeResponse();
            response.setId(dto.getId());
            response.setSecurityCode(dto.getSecurityCode());
            response.setSecurityMarket(dto.getSecurityMarket());
            response.setSecurityType(dto.getSecurityType());
            response.setSubType(dto.getSubType());
            return response;
        });
    }

    @Override
    @Transactional
    public IPage<ListSubscribeResponse> findList(ListSubscribeRequest request) {
        Page<SubDto> pagination = Page.of(1, 10);
        if (request.getCurrent() != null) {
            pagination.setCurrent(request.getCurrent());
        }
        if (request.getSize() != null) {
            pagination.setSize(request.getSize());
        }
        return getBaseMapper().findAllWithAggregateSubTypes(pagination).convert(subDto -> {
            ListSubscribeResponse response = new ListSubscribeResponse();
            response.setSecurityName(subDto.getSecurityName());
            response.setSecurityMarket(subDto.getSecurityMarket());
            response.setSecurityType(subDto.getSecurityType());
            response.setSecurityCode(subDto.getSecurityCode());
            response.setSubTypes(subDto.getSubTypes());
            return response;
        });
    }
}




