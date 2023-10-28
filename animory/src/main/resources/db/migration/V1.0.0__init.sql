/* Version Naming Rule

    1.0.0 으로 시작.
    Major.Minor.Patch

    Major : 주요한 데이터 구조의 변경이 발생한 경우
            테이블 추가 및 삭제

    Minor : 기존 버전과 크게 다르지 않은 변경 사항
            테이블 변경 : 필드 추가, 삭제, 이름 또는 타입 변경 등

    Patch : 기존 버전과 호환되는 버그 수정
            의미 상의 변경이 아닌, 실수를 바로 잡기위한 수정사항 등.

 */


 /* File Naming Rule
    SQL KEYWORD를 따르도록 한다.

    Major : Table 추가 및 삭제 시
            create_tableName.sql
            drop_tableName.sql

    Minor : Table 변경 시
            alter_tableName_rename_newTableName.sql : 테이블 이름 변경
            alter_tableName_add_column.sql
            alter_tableName_drop_column.sql
            alter_tableName_modify_column.sql
            alter_tableName_change_column.sql : 필드 이름까지 변경

    Patch : 사소한 변경
            Minor 의 Rule을 따르도록 한다.
  */