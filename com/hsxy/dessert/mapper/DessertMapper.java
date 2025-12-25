package com.hsxy.dessert.mapper;

import com.hsxy.dessert.entity.Dessert;
import com.hsxy.dessert.entity.DessertDetail;
import com.hsxy.dessert.entity.SearchCondition;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DessertMapper {
    @Select("select d.id,d.name,photoUrl,price,d.descp,release_date releaseDate, "
    + " cat_id CategoryId,c.name categoryName "
    + " from dessert d left join category c on d.cat_id = c.id")
    List<DessertDetail> getAll();

    @Select("<script>" +
            "select d.id,d.name,photoUrl,price,d.descp,release_date releaseDate," +
            "cat_id CategoryId,c.name categoryName " +
            "from dessert d left join category c on d.cat_id = c.id " +
            "<where>" +
            "<if test='catId != 0'>AND cat_id = #{catId} </if>" +
            "<if test='name != null'>and d.name like CONCAT('%',#{name},'%') </if>" +
            "<if test='descp != null'>and d.descp like CONCAT('%', #{descp},'%')</if>" +
            "<if test='price1 != null and price2 != null'>" +
            " and (price between #{price1} and #{price2})</if>" +
            "</where>" +
            "</script>")
    List<DessertDetail> search(SearchCondition condition);

    @Insert("insert into dessert(name,photoUrl,price,descp,release_date, cat_id) "
            + "values(#{name},#{photoUrl},#{price},#{descp},#{releaseDate},#{catId})")
    int save(Dessert dessert);

    @Select("select id,name,photoUrl,price,descp,release_date releaseDate,cat_id catId " +
            "from dessert where id=#{id}")
    Dessert get(Integer id);

    @Update("<script>update dessert set name=#{name}," +
            // 动态：photo可能不做修改
            "<if test='photoUrl != null'>photoUrl=#{photoUrl},</if>" +
            "price=#{price},descp=#{descp},release_date=#{releaseDate},cat_id=#{catId} " +
            "where id=#{id}</script>")
    int edit(Dessert dessert);

    @Delete("delete from dessert where id=#{id}")
    int remove(Integer id);

    @Select("select d.id,d.name,photoUrl,price,d.descp,release_date releaseDate," +
            "cat_id CategoryId,c.name categoryName " +
            "from dessert d left join category c on d.cat_id = c.id " +
            "order by d.release_date desc limit #{row}")
    List<DessertDetail> getRleaseNew(Integer row);
}
