package hpcrusher.repository.converter;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.ArrayList;

/**
 * @author liebl
 */
@Converter(autoApply = true)
public class IntegerArrayConverter implements AttributeConverter<int[], String> {

    @Override
    public String convertToDatabaseColumn(int[] entityValue) {
        JSONSerializer jsonSerializer = new JSONSerializer();
        return jsonSerializer.serialize(entityValue);
    }

    @Override
    public int[] convertToEntityAttribute(String dbValue) {
        JSONDeserializer<ArrayList<Long>> jsonDeserializer = new JSONDeserializer<>();
        ArrayList<Long> deserialize = jsonDeserializer.deserialize(dbValue);
        return deserialize.stream().mapToInt(Long::intValue).toArray();
    }
}