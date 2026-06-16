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
import io.futakotome.trade.dto.SnapshotIndexExDto;
import io.futakotome.trade.mapper.pg.SnapshotIndexExDtoMapper;
import io.futakotome.trade.service.SnapshotIndexExDtoService;
import org.apache.ibatis.binding.MapperMethod;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Objects;

/**
 * @author 86131
 * @description 针对表【t_snapshot_index_ex】的数据库操作Service实现
 * @createDate 2025-02-20 09:36:01
 */
@Service
public class SnapshotIndexExDtoServiceImpl extends ServiceImpl<SnapshotIndexExDtoMapper, SnapshotIndexExDto>
        implements SnapshotIndexExDtoService {
    @Override
    public boolean saveOrUpdateBatch(Collection<SnapshotIndexExDto> indexExDtos, int batchSize) {
        TableInfo tableInfo = TableInfoHelper.getTableInfo(this.entityClass);
        String updatePropertyMarket = "market";
        String updatePropertyCode = "code";
        return this.executeBatch(indexExDtos, batchSize, (sqlSession, indexExDto) -> {
            Object marketVal = ReflectionKit.getFieldValue(indexExDto, updatePropertyMarket);
            Object codeVal = ReflectionKit.getFieldValue(indexExDto, updatePropertyCode);
            LambdaQueryWrapper<SnapshotIndexExDto> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SnapshotIndexExDto::getMarket, marketVal)
                    .eq(SnapshotIndexExDto::getCode, codeVal);
            if (StringUtils.checkValNotNull(codeVal) && StringUtils.checkValNotNull(marketVal)
                    && Objects.nonNull(this.getOne(queryWrapper))) {
                MapperMethod.ParamMap<Object> param = new MapperMethod.ParamMap<>();
                param.put(Constants.WRAPPER, updateWrapper(indexExDto));
                param.put(Constants.ENTITY, indexExDto);
                sqlSession.update(tableInfo.getSqlStatement(SqlMethod.UPDATE.getMethod()), param);
            } else {
                sqlSession.insert(tableInfo.getSqlStatement(SqlMethod.INSERT_ONE.getMethod()), indexExDto);
            }
        });
    }

    private LambdaUpdateWrapper<SnapshotIndexExDto> updateWrapper(SnapshotIndexExDto indexExDto) {
        LambdaUpdateWrapper<SnapshotIndexExDto> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(SnapshotIndexExDto::getMarket, indexExDto.getMarket())
                .eq(SnapshotIndexExDto::getCode, indexExDto.getCode());
        return updateWrapper;
    }
}




