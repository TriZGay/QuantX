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
import io.futakotome.trade.dto.SnapshotPlateExDto;
import io.futakotome.trade.dto.SnapshotWarrantExDto;
import io.futakotome.trade.mapper.pg.SnapshotWarrantExDtoMapper;
import io.futakotome.trade.service.SnapshotWarrantExDtoService;
import org.apache.ibatis.binding.MapperMethod;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Objects;

/**
 * @author 86131
 * @description 针对表【t_snapshot_warrant_ex】的数据库操作Service实现
 * @createDate 2025-02-20 09:36:01
 */
@Service
public class SnapshotWarrantExDtoServiceImpl extends ServiceImpl<SnapshotWarrantExDtoMapper, SnapshotWarrantExDto>
        implements SnapshotWarrantExDtoService {
    @Override
    public boolean saveOrUpdateBatch(Collection<SnapshotWarrantExDto> warrantExDtos, int batchSize) {
        TableInfo tableInfo = TableInfoHelper.getTableInfo(this.entityClass);
        String updatePropertyMarket = "ownerMarket";
        String updatePropertyCode = "ownerCode";
        return this.executeBatch(warrantExDtos, batchSize, (sqlSession, warrantExDto) -> {
            Object marketVal = ReflectionKit.getFieldValue(warrantExDto, updatePropertyMarket);
            Object codeVal = ReflectionKit.getFieldValue(warrantExDto, updatePropertyCode);
            LambdaQueryWrapper<SnapshotWarrantExDto> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SnapshotWarrantExDto::getOwnerMarket, marketVal)
                    .eq(SnapshotWarrantExDto::getOwnerCode, codeVal);
            if (StringUtils.checkValNotNull(codeVal) && StringUtils.checkValNotNull(marketVal)
                    && Objects.nonNull(this.getOne(queryWrapper))) {
                MapperMethod.ParamMap<Object> param = new MapperMethod.ParamMap<>();
                param.put(Constants.WRAPPER, updateWrapper(warrantExDto));
                param.put(Constants.ENTITY, warrantExDto);
                sqlSession.update(tableInfo.getSqlStatement(SqlMethod.UPDATE.getMethod()), param);
            } else {
                sqlSession.insert(tableInfo.getSqlStatement(SqlMethod.INSERT_ONE.getMethod()), warrantExDto);
            }
        });
    }

    private LambdaUpdateWrapper<SnapshotWarrantExDto> updateWrapper(SnapshotWarrantExDto warrantExDto) {
        LambdaUpdateWrapper<SnapshotWarrantExDto> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(SnapshotWarrantExDto::getOwnerMarket, warrantExDto.getOwnerMarket())
                .eq(SnapshotWarrantExDto::getOwnerCode, warrantExDto.getOwnerCode());
        return updateWrapper;
    }
}




