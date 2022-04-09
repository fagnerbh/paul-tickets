set @i = 0;
while @i < 300
    insert into sector (sec_id, vnu_id) values (cast(@i AS CHAR), 'a9df7835-cb72-4d60-8397-7860aad4806d');
    set @a = 1;
    while @a < 1000
      insert into seat (sec_id, sea_num) values (cast(@i AS CHAR), @a);
      set @a = @a + 1;
    end while;     

    set @i = @i + 1;
end while;  
