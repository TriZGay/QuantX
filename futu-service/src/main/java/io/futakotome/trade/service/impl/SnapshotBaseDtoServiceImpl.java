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
import io.futakotome.trade.mapper.pg.SnapshotBaseDtoMapper;
import io.futakotome.trade.service.SnapshotBaseDtoService;
import org.apache.ibatis.binding.MapperMethod;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Objects;

/**
 * @author 86131
 * @description 针对表【t_snapshot_base】的数据库操作Service实现
 * @createDate 2025-02-20 09:36:01
 */
@Service
public class SnapshotBaseDtoServiceImpl extends ServiceImpl<SnapshotBaseDtoMapper, SnapshotBaseDto>
        implements SnapshotBaseDtoService {

    @Override
    public boolean saveOrUpdateBatch(Collection<SnapshotBaseDto> baseDtos, int batchSize) {
        TableInfo tableInfo = TableInfoHelper.getTableInfo(this.entityClass);
        String updatePropertyMarket = "market";
        String updatePropertyCode = "code";
        return this.executeBatch(baseDtos, batchSize, (sqlSession, baseDto) -> {
            Object marketVal = ReflectionKit.getFieldValue(baseDto, updatePropertyMarket);
            Object codeVal = ReflectionKit.getFieldValue(baseDto, updatePropertyCode);
            LambdaQueryWrapper<SnapshotBaseDto> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SnapshotBaseDto::getMarket, marketVal)
                    .eq(SnapshotBaseDto::getCode, codeVal);
            if (StringUtils.checkValNotNull(codeVal) && StringUtils.checkValNotNull(marketVal)
                    && Objects.nonNull(this.getOne(queryWrapper))) {
                MapperMethod.ParamMap<Object> param = new MapperMethod.ParamMap<>();
                param.put(Constants.WRAPPER, updateWrapper(baseDto));
                param.put(Constants.ENTITY, baseDto);
                sqlSession.update(tableInfo.getSqlStatement(SqlMethod.UPDATE.getMethod()), param);
            } else {
                sqlSession.insert(tableInfo.getSqlStatement(SqlMethod.INSERT_ONE.getMethod()), baseDto);
            }
        });
    }

    private LambdaUpdateWrapper<SnapshotBaseDto> updateWrapper(SnapshotBaseDto baseDto) {
        LambdaUpdateWrapper<SnapshotBaseDto> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(SnapshotBaseDto::getMarket, baseDto.getMarket())
                .eq(SnapshotBaseDto::getCode, baseDto.getCode());
        return updateWrapper;
    }
}




