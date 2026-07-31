package io.futakotome.trade.utils.converter;

import com.google.gson.*;
import io.futakotome.trade.domain.code.FinancialType;
import io.futakotome.trade.dto.message.OperationalEfficiencyItem;

import java.lang.reflect.Type;

public class OperationalEfficiencyItemConverter implements JsonDeserializer<OperationalEfficiencyItem> {
    @Override
    public OperationalEfficiencyItem deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject j = jsonElement.getAsJsonObject();
        Integer fiscalYear = j.get("fiscalYear").getAsInt();
        String financialType = j.get("financialType").getAsString();
        String financialTypeStr = FinancialType.getName(financialType);
        String periodText = j.get("periodText").getAsString();
        Long endDate = j.get("endDate").getAsLong();
        String endDateStr = j.get("endDateStr").getAsString();
        Long employeeNum = j.get("employeeNum").getAsLong();
        Double employeeNumYoy = j.get("employeeNumYoy").getAsDouble();
        Double incomePerCapita = j.get("incomePerCapita").getAsDouble();
        Double incomePerCapitaYoy = j.get("incomePerCapitaYoy").getAsDouble();
        Double profitPerCapita = j.get("profitPerCapita").getAsDouble();
        Double profitPerCapitaYoy = j.get("profitPerCapitaYoy").getAsDouble();
        Double netProfitPerCapita = j.get("netProfitPerCapita").getAsDouble();
        Double netProfitPerCapitaYoy = j.get("netProfitPerCapitaYoy").getAsDouble();

        return new OperationalEfficiencyItem(
                fiscalYear,
                financialType,
                financialTypeStr,
                periodText,
                endDate,
                endDateStr,
                employeeNum,
                employeeNumYoy,
                incomePerCapita,
                incomePerCapitaYoy,
                profitPerCapita,
                profitPerCapitaYoy,
                netProfitPerCapita,
                netProfitPerCapitaYoy);
    }
}
