package hpcrusher.repository.converter;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author David Liebl
 */

@Converter(autoApply = true)
public class IntegerMultiArrayConverter implements AttributeConverter<int[][], String> {

    @Override
    public String convertToDatabaseColumn(int[][] entityValue) {
        JSONSerializer jsonSerializer = new JSONSerializer();
        return jsonSerializer.deepSerialize(entityValue);
    }

    @Override
    public int[][] convertToEntityAttribute(String dbValue) {
        JSONDeserializer<ArrayList<ArrayList<Long>>> jsonDeserializer = new JSONDeserializer<>();
        ArrayList<ArrayList<Long>> deserialize = jsonDeserializer.deserialize(dbValue);
        int[][] ret = new int[deserialize.size()][];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = deserialize.get(i).stream().mapToInt(Long::intValue).toArray();
        }
        return ret;
    }
}
