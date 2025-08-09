package net.lotte.chamomile.admin.common.response;

import java.lang.reflect.Field;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@SuperBuilder
public class TimeAuthorResponse {
    private LocalDateTime sysInsertDtm;
    private String sysInsertUserId;
    private LocalDateTime sysUpdateDtm;
    private String sysUpdateUserId;

    public static TimeAuthorResponse getTimeAuthor(Object instance) {
        LocalDateTime insertDateTime = null;
        String insertAuthor = null;
        LocalDateTime updateDateTime = null;
        String updateAuthor = null;

        try {
            Class<?> clazz = instance.getClass();
            clazz.getFields();
            Field field1 = getFieldIfExists(clazz, "sysInsertDtm");
            if (field1 != null) {
                field1.setAccessible(true);
                Object insertDateTimeObj = field1.get(instance);
                if (insertDateTimeObj != null) {
                    insertDateTime = (LocalDateTime) insertDateTimeObj;
                }
            }

            Field field2 = getFieldIfExists(clazz, "sysInsertUserId");
            if (field2 != null) {
                field2.setAccessible(true);
                Object insertAuthorObj = field2.get(instance);
                if (insertAuthorObj != null) {
                    insertAuthor = (String) insertAuthorObj;
                }
            }

            Field field3 = getFieldIfExists(clazz, "sysUpdateDtm");
            if (field3 != null) {
                field3.setAccessible(true);
                Object updateDateTimeObj = field3.get(instance);
                if (updateDateTimeObj != null) {
                    updateDateTime = (LocalDateTime) updateDateTimeObj;
                }
            }

            Field field4 = getFieldIfExists(clazz, "sysUpdateUserId");
            if (field4 != null) {
                field4.setAccessible(true);
                Object updateAuthorObj = field4.get(instance);
                if (updateAuthorObj != null) {
                    updateAuthor = (String) updateAuthorObj;
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException("timeAuthor 변환 오류.", e);
        }

        return new TimeAuthorResponse(insertDateTime, insertAuthor, updateDateTime, updateAuthor);
    }

    private static Field getFieldIfExists(Class<?> clazz, String fieldName) {
        try {
            return clazz.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            return null;
        }
    }
}
