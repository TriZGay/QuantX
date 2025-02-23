package io.futakotome.trade.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.futakotome.trade.dto.SnapshotBaseDto;
import io.futakotome.trade.dto.SnapshotEquityExDto;
import io.futakotome.trade.mapper.SnapshotEquityExDtoMapper;
import io.futakotome.trade.service.SnapshotEquityExDtoService;
import org.apache.ibatis.binding.MapperMethod;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Objects;

/**
 * @author 86131
 * @description 针对表【t_snapshot_equity_ex】的数据库操作Service实现
 * @createDate 2025-02-20 09:36:01
 */
@Service
public class SnapshotEquityExDtoServiceImpl extends ServiceImpl<SnapshotEquityExDtoMapper, SnapshotEquityExDto>
        implements SnapshotEquityExDtoService {
    @Override
    public boolean saveOrUpdateBatch(Collection<SnapshotEquityExDto> equityExDtos, int batchSize) {
        TableInfo tableInfo = TableInfoHelper.getTableInfo(this.entityClass);
        String updatePropertyMarket = "market";
        String updatePropertyCode = "code";
        return this.executeBatch(equityExDtos, batchSize, (sqlSession, equityExDto) -> {
            Object marketVal = ReflectionKit.getFieldValue(equityExDto, updatePropertyMarket);
            Object codeVal = ReflectionKit.getFieldValue(equityExDto, updatePropertyCode);
            LambdaQueryWrapper<SnapshotEquityExDto> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SnapshotEquityExDto::getMarket, marketVal)
                    .eq(SnapshotEquityExDto::getCode, codeVal);
            if (StringUtils.checkValNotNull(codeVal) && StringUtils.checkValNotNull(marketVal)
                    && Objects.nonNull(this.getOne(queryWrapper))) {
                MapperMethod.ParamMap<Object> param = new MapperMethod.ParamMap<>();
                param.put(Constants.WRAPPER, updateWrapper(equityExDto));
                param.put(Constants.ENTITY, equityExDto);
                sqlSession.update(tableInfo.getSqlStatement(SqlMethod.UPDATE.getMethod()), param);
            } else {
                sqlSession.insert(tableInfo.getSqlStatement(SqlMethod.INSERT_ONE.getMethod()), equityExDto);
            }
        });
    }

    private LambdaUpdateWrapper<SnapshotEquityExDto> updateWrapper(SnapshotEquityExDto equityExDto) {
        LambdaUpdateWrapper<SnapshotEquityExDto> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(SnapshotEquityExDto::getMarket, equityExDto.getMarket())
                .eq(SnapshotEquityExDto::getCode, equityExDto.getCode());
        return updateWrapper;
    }
}




