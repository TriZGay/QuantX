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
import io.futakotome.trade.dto.SnapshotOptionExDto;
import io.futakotome.trade.mapper.pg.SnapshotOptionExDtoMapper;
import io.futakotome.trade.service.SnapshotOptionExDtoService;
import org.apache.ibatis.binding.MapperMethod;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Objects;

/**
 * @author 86131
 * @description 针对表【t_snapshot_option_ex】的数据库操作Service实现
 * @createDate 2025-02-20 09:36:01
 */
@Service
public class SnapshotOptionExDtoServiceImpl extends ServiceImpl<SnapshotOptionExDtoMapper, SnapshotOptionExDto>
        implements SnapshotOptionExDtoService {
    @Override
    public boolean saveOrUpdateBatch(Collection<SnapshotOptionExDto> optionExDtos, int batchSize) {
        TableInfo tableInfo = TableInfoHelper.getTableInfo(this.entityClass);
        String updatePropertyMarket = "ownerMarket";
        String updatePropertyCode = "ownerCode";
        return this.executeBatch(optionExDtos, batchSize, (sqlSession, optionExDto) -> {
            Object marketVal = ReflectionKit.getFieldValue(optionExDto, updatePropertyMarket);
            Object codeVal = ReflectionKit.getFieldValue(optionExDto, updatePropertyCode);
            LambdaQueryWrapper<SnapshotOptionExDto> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SnapshotOptionExDto::getOwnerMarket, marketVal)
                    .eq(SnapshotOptionExDto::getOwnerCode, codeVal);
            if (StringUtils.checkValNotNull(codeVal) && StringUtils.checkValNotNull(marketVal)
                    && Objects.nonNull(this.getOne(queryWrapper))) {
                MapperMethod.ParamMap<Object> param = new MapperMethod.ParamMap<>();
                param.put(Constants.WRAPPER, updateWrapper(optionExDto));
                param.put(Constants.ENTITY, optionExDto);
                sqlSession.update(tableInfo.getSqlStatement(SqlMethod.UPDATE.getMethod()), param);
            } else {
                sqlSession.insert(tableInfo.getSqlStatement(SqlMethod.INSERT_ONE.getMethod()), optionExDto);
            }
        });
    }

    private LambdaUpdateWrapper<SnapshotOptionExDto> updateWrapper(SnapshotOptionExDto optionExDto) {
        LambdaUpdateWrapper<SnapshotOptionExDto> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(SnapshotOptionExDto::getOwnerMarket, optionExDto.getOwnerMarket())
                .eq(SnapshotOptionExDto::getOwnerCode, optionExDto.getOwnerCode());
        return updateWrapper;
    }
}




