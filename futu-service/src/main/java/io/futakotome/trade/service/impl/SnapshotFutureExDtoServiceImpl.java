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
import io.futakotome.trade.dto.SnapshotFutureExDto;
import io.futakotome.trade.dto.SnapshotPlateExDto;
import io.futakotome.trade.mapper.pg.SnapshotFutureExDtoMapper;
import io.futakotome.trade.service.SnapshotFutureExDtoService;
import org.apache.ibatis.binding.MapperMethod;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Objects;

/**
 * @author 86131
 * @description 针对表【t_snapshot_future_ex】的数据库操作Service实现
 * @createDate 2025-02-20 09:36:01
 */
@Service
public class SnapshotFutureExDtoServiceImpl extends ServiceImpl<SnapshotFutureExDtoMapper, SnapshotFutureExDto>
        implements SnapshotFutureExDtoService {
    @Override
    public boolean saveOrUpdateBatch(Collection<SnapshotFutureExDto> futureExDtos, int batchSize) {
        TableInfo tableInfo = TableInfoHelper.getTableInfo(this.entityClass);
        String updatePropertyMarket = "market";
        String updatePropertyCode = "code";
        return this.executeBatch(futureExDtos, batchSize, (sqlSession, futureExDto) -> {
            Object marketVal = ReflectionKit.getFieldValue(futureExDto, updatePropertyMarket);
            Object codeVal = ReflectionKit.getFieldValue(futureExDto, updatePropertyCode);
            LambdaQueryWrapper<SnapshotFutureExDto> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SnapshotFutureExDto::getMarket, marketVal)
                    .eq(SnapshotFutureExDto::getCode, codeVal);
            if (StringUtils.checkValNotNull(codeVal) && StringUtils.checkValNotNull(marketVal)
                    && Objects.nonNull(this.getOne(queryWrapper))) {
                MapperMethod.ParamMap<Object> param = new MapperMethod.ParamMap<>();
                param.put(Constants.WRAPPER, updateWrapper(futureExDto));
                param.put(Constants.ENTITY, futureExDto);
                sqlSession.update(tableInfo.getSqlStatement(SqlMethod.UPDATE.getMethod()), param);
            } else {
                sqlSession.insert(tableInfo.getSqlStatement(SqlMethod.INSERT_ONE.getMethod()), futureExDto);
            }
        });
    }

    private LambdaUpdateWrapper<SnapshotFutureExDto> updateWrapper(SnapshotFutureExDto futureExDto) {
        LambdaUpdateWrapper<SnapshotFutureExDto> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(SnapshotFutureExDto::getMarket, futureExDto.getMarket())
                .eq(SnapshotFutureExDto::getCode, futureExDto.getCode());
        return updateWrapper;
    }
}




