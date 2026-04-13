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
import io.futakotome.trade.dto.SnapshotTrustExDto;
import io.futakotome.trade.mapper.pg.SnapshotTrustExDtoMapper;
import io.futakotome.trade.service.SnapshotTrustExDtoService;
import org.apache.ibatis.binding.MapperMethod;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Objects;

/**
 * @author 86131
 * @description 针对表【t_snapshot_trust_ex】的数据库操作Service实现
 * @createDate 2025-02-20 09:36:01
 */
@Service
public class SnapshotTrustExDtoServiceImpl extends ServiceImpl<SnapshotTrustExDtoMapper, SnapshotTrustExDto>
        implements SnapshotTrustExDtoService {
    @Override
    public boolean saveOrUpdateBatch(Collection<SnapshotTrustExDto> trustExDtos, int batchSize) {
        TableInfo tableInfo = TableInfoHelper.getTableInfo(this.entityClass);
        String updatePropertyMarket = "market";
        String updatePropertyCode = "code";
        return this.executeBatch(trustExDtos, batchSize, (sqlSession, trustExDto) -> {
            Object marketVal = ReflectionKit.getFieldValue(trustExDto, updatePropertyMarket);
            Object codeVal = ReflectionKit.getFieldValue(trustExDto, updatePropertyCode);
            LambdaQueryWrapper<SnapshotTrustExDto> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SnapshotTrustExDto::getMarket, marketVal)
                    .eq(SnapshotTrustExDto::getCode, codeVal);
            if (StringUtils.checkValNotNull(codeVal) && StringUtils.checkValNotNull(marketVal)
                    && Objects.nonNull(this.getOne(queryWrapper))) {
                MapperMethod.ParamMap<Object> param = new MapperMethod.ParamMap<>();
                param.put(Constants.WRAPPER, updateWrapper(trustExDto));
                param.put(Constants.ENTITY, trustExDto);
                sqlSession.update(tableInfo.getSqlStatement(SqlMethod.UPDATE.getMethod()), param);
            } else {
                sqlSession.insert(tableInfo.getSqlStatement(SqlMethod.INSERT_ONE.getMethod()), trustExDto);
            }
        });
    }

    private LambdaUpdateWrapper<SnapshotTrustExDto> updateWrapper(SnapshotTrustExDto trustExDto) {
        LambdaUpdateWrapper<SnapshotTrustExDto> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(SnapshotTrustExDto::getMarket, trustExDto.getMarket())
                .eq(SnapshotTrustExDto::getCode, trustExDto.getCode());
        return updateWrapper;
    }
}




