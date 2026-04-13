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
import io.futakotome.trade.dto.SnapshotPlateExDto;
import io.futakotome.trade.mapper.pg.SnapshotPlateExDtoMapper;
import io.futakotome.trade.service.SnapshotPlateExDtoService;
import org.apache.ibatis.binding.MapperMethod;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Objects;

/**
 * @author 86131
 * @description 针对表【t_snapshot_plate_ex】的数据库操作Service实现
 * @createDate 2025-02-20 09:36:01
 */
@Service
public class SnapshotPlateExDtoServiceImpl extends ServiceImpl<SnapshotPlateExDtoMapper, SnapshotPlateExDto>
        implements SnapshotPlateExDtoService {
    @Override
    public boolean saveOrUpdateBatch(Collection<SnapshotPlateExDto> plateExDtos, int batchSize) {
        TableInfo tableInfo = TableInfoHelper.getTableInfo(this.entityClass);
        String updatePropertyMarket = "market";
        String updatePropertyCode = "code";
        return this.executeBatch(plateExDtos, batchSize, (sqlSession, plateExDto) -> {
            Object marketVal = ReflectionKit.getFieldValue(plateExDto, updatePropertyMarket);
            Object codeVal = ReflectionKit.getFieldValue(plateExDto, updatePropertyCode);
            LambdaQueryWrapper<SnapshotPlateExDto> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SnapshotPlateExDto::getMarket, marketVal)
                    .eq(SnapshotPlateExDto::getCode, codeVal);
            if (StringUtils.checkValNotNull(codeVal) && StringUtils.checkValNotNull(marketVal)
                    && Objects.nonNull(this.getOne(queryWrapper))) {
                MapperMethod.ParamMap<Object> param = new MapperMethod.ParamMap<>();
                param.put(Constants.WRAPPER, updateWrapper(plateExDto));
                param.put(Constants.ENTITY, plateExDto);
                sqlSession.update(tableInfo.getSqlStatement(SqlMethod.UPDATE.getMethod()), param);
            } else {
                sqlSession.insert(tableInfo.getSqlStatement(SqlMethod.INSERT_ONE.getMethod()), plateExDto);
            }
        });
    }

    private LambdaUpdateWrapper<SnapshotPlateExDto> updateWrapper(SnapshotPlateExDto plateExDto) {
        LambdaUpdateWrapper<SnapshotPlateExDto> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(SnapshotPlateExDto::getMarket, plateExDto.getMarket())
                .eq(SnapshotPlateExDto::getCode, plateExDto.getCode());
        return updateWrapper;
    }
}




