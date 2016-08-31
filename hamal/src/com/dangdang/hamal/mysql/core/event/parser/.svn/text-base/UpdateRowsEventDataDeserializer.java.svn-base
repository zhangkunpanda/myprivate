/*
 *  
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dangdang.hamal.mysql.core.event.parser;

import com.dangdang.hamal.io.ByteArrayInputStream;
import com.dangdang.hamal.mysql.core.event.TableMapEventData;
import com.dangdang.hamal.mysql.core.event.UpdateRowsEventData;

import java.io.IOException;
import java.io.Serializable;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.Map;

/**
 * @author  
 */
public class UpdateRowsEventDataDeserializer extends AbstractRowsEventDataDeserializer<UpdateRowsEventData> {

    private boolean mayContainExtraInformation;

    public UpdateRowsEventDataDeserializer(Map<Long, TableMapEventData> tableMapEventByTableId) {
        super(tableMapEventByTableId);
    }

    public UpdateRowsEventDataDeserializer setMayContainExtraInformation(boolean mayContainExtraInformation) {
        this.mayContainExtraInformation = mayContainExtraInformation;
        return this;
    }

    @Override
    public UpdateRowsEventData deserialize(ByteArrayInputStream inputStream) throws IOException {
        UpdateRowsEventData eventData = new UpdateRowsEventData();
        eventData.setTableId(inputStream.readLong(6));
        inputStream.skip(2); // reserved
        if (mayContainExtraInformation) {
            int extraInfoLength = inputStream.readInteger(2);
            inputStream.skip(extraInfoLength - 2);
        }
        int numberOfColumns = inputStream.readPackedInteger();
        eventData.setIncludedColumnsBeforeUpdate(inputStream.readBitSet(numberOfColumns, true));
        eventData.setIncludedColumns(inputStream.readBitSet(numberOfColumns, true));
        eventData.setRows(deserializeRows(eventData, inputStream));
        return eventData;
    }

    private List<Map.Entry<Serializable[], Serializable[]>> deserializeRows(UpdateRowsEventData eventData,
            ByteArrayInputStream inputStream) throws IOException {
        long tableId = eventData.getTableId();
        BitSet includedColumnsBeforeUpdate = eventData.getIncludedColumnsBeforeUpdate(),
               includedColumns = eventData.getIncludedColumns();
        List<Map.Entry<Serializable[], Serializable[]>> rows =
                new ArrayList<Map.Entry<Serializable[], Serializable[]>>();
        while (inputStream.available() > 0) {
            rows.add(new AbstractMap.SimpleEntry<Serializable[], Serializable[]>(
                    deserializeOneRow(tableId, includedColumnsBeforeUpdate, inputStream),
                    deserializeOneRow(tableId, includedColumns, inputStream)
            ));
        }
        return rows;
    }

}
