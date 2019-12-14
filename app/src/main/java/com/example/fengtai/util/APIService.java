package com.example.fengtai.util;

import com.example.fengtai.entity.GetMessageResylt;
import com.example.fengtai.entity.RemakePassWord;
import com.example.fengtai.entity.personalcenter.FindDetailResult;
import com.example.fengtai.entity.personalcenter.FindResult;
import com.example.fengtai.entity.HomeResult;
import com.example.fengtai.entity.ImgResult;
import com.example.fengtai.entity.LoginResult;
import com.example.fengtai.entity.UserInfoResult;
import com.example.fengtai.entity.base.Result;
import com.example.fengtai.entity.breed.BreedBaseMsg;
import com.example.fengtai.entity.breed.BreedClassResult;
import com.example.fengtai.entity.breed.BreedIndexResult;
import com.example.fengtai.entity.breed.BreedsResult;
import com.example.fengtai.entity.breed.Supplier;
import com.example.fengtai.entity.breed.Variety;
import com.example.fengtai.entity.farmdoc.Compensate;
import com.example.fengtai.entity.farmdoc.HuKou;
import com.example.fengtai.entity.farmdoc.HuKouUser;
import com.example.fengtai.entity.farmdoc.InOut;
import com.example.fengtai.entity.farmdoc.InfoCount;
import com.example.fengtai.entity.farmdoc.Personnel;
import com.example.fengtai.entity.farmdoc.Plant;
import com.example.fengtai.entity.Region;
import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

/*
=================
   API：get/post
=================
 */

public interface APIService {
    //   登录
    @Multipart
    @POST("Api/ApiLogin/store_api")
    Call<Result<LoginResult>> loginUser(@Part("username") RequestBody user, @Part("password") RequestBody password);

    //注册
    @Multipart
    @POST("Api/User/store")
    Call<Result<LoginResult>> enrollUser(@Part("username") RequestBody user, @Part("password") RequestBody password, @Part("phone") RequestBody phone);

    //获取验证码
    @GET("Api/Alisms/code.html")
    Call<GetMessageResylt> getMessage(@Query("phone") String phone);

    //重置密码
    @Multipart
    @POST("Api/ApiLogin/resetpassword")
    Call<Result<RemakePassWord>> remakePass(@Part("phone") RequestBody phone,@Part("password") RequestBody password);
    //获取首页
    @Multipart
    @POST("Api/ApiDevices/product_index")
    Call<Result<ArrayList<HomeResult>>> getHome(@Part("user_id") RequestBody userId);

    /*
     *
     *户口管理
     *
     * */
    //获取个人信息
    @GET("Api/ApiUser/info")
    Call<Result<HuKouUser>> getUser(@Query("user_id") String userId);


    //户口查询
    @GET("Api/Hukou/find")
    Call<Result<HuKou>> getHuKou(@Query("id") String userId);

    //个人首页统计数据
    @GET("Api/Twophasecount/hukou?user_id")
    Call<Result<InfoCount>> getCount(@Query("user_id") String id);

    //户口查询
    @GET("Api/Hukou/hukoulist")
    Call<Result<ArrayList<HuKou>>> getHuKouList(@Query("user_id") String userId);

    //户口信息补充  todo -age
    @Multipart
    @POST("Api/Hukou/add")
    Call<Result<HuKou>> enrollHuKou(@Part("user_id") RequestBody userId, @Part("name") RequestBody name, @Part("relations") RequestBody relations, @Part("tel") RequestBody tel, @Part("labour_type") RequestBody labourType);

    //户口信息完善
    @Multipart
    @POST("Api/Hukou/edit")
    Call<Result<HuKou>> editHuKou(
            @Part("id") RequestBody id,
            @Part("idcard_front") RequestBody idcard_front,
            @Part("idcard_name") RequestBody idcard_name,
            @Part("idcard_side") RequestBody idcard_gender,
            @Part("birth_date") RequestBody birth_date,
            @Part("idcard") RequestBody idcard,
            @Part("sheng") RequestBody sheng,
            @Part("shi") RequestBody shi,
            @Part("xian") RequestBody xian,
            @Part("xiangxi") RequestBody xiangxi,
            @Part("age") RequestBody age,
            @Part("idcard_gender") RequestBody sex
    );

    //编辑户口信息
    @Multipart
    @POST("Api/Hukou/editis")
    Call<Result<HuKou>> editsHuKou(@PartMap Map<String, RequestBody> params);


    /*
     * 成员管理
     */

    //多查
    @GET("Api/Personnel/index")
    Call<Result<ArrayList<Personnel>>> getPersonnels(@Query("user_id") String userId);

    //单查
    @GET("Api/Personnel/find")
    Call<Result<Personnel>> getPersonnel(@Query("id") String id);

    //增
    @Multipart
    @POST("Api/Personnel/add")
    Call<Result<Personnel>> addPersonnel(
            @Part("user_id") RequestBody userId,
            @Part("img") RequestBody img,
            @Part("call") RequestBody call,
            @Part("name") RequestBody name,
            @Part("per_relations") RequestBody perRelations,
//            @Part("name") RequestBody perRelations,
            @Part("tel") RequestBody tel,
            @Part("labour_type") RequestBody labourType
    );

    //删
    @GET("Api/Personnel/DelCar")
    Call<Result<String>> delPersonnel(@Query("id") String id);

    //改
    @Multipart
    @POST("Api/Personnel/editis")
    Call<Result<Personnel>> editPeronnel(
            @Part("user_id") RequestBody userId,
            @Part("id") RequestBody id,
            @Part("img") RequestBody img,
            @Part("call") RequestBody call,
            @Part("name") RequestBody name,
            @Part("per_relations") RequestBody perRelations,
            @Part("tel") RequestBody tel,
            @Part("labour_type") RequestBody labourType
    );


    /*
     * 草原面积
     *
     */
    //多查
    @GET("Api/Steppearea/index")
    Call<Result<ArrayList<Plant>>> getStes(@Query("user_id") String userId);

    //单查
    @GET("Api/Steppearea/find")
    Call<Result<Plant>> getSte(@Query("id") String id);

    //增
    @Multipart
    @POST("Api/Steppearea/add")
    Call<Result<Plant>> addSte(
            @Part("user_id") RequestBody userId,
            @Part("name") RequestBody name,
            @Part("area") RequestBody area,
            @Part("area_type") RequestBody areaType,
            @Part("ste_type") RequestBody steType
    );

    //删
    @GET("Api/Steppearea/DelCar")
    Call<Result<String>> delSte(@Query("id") String id);

    //改
    @Multipart
    @POST("Api/Steppearea/editis")
    Call<Result<Plant>> editSte(
            @Part("user_id") RequestBody userId,
            @Part("id") RequestBody id,
            @Part("name") RequestBody name,
            @Part("area") RequestBody area,
            @Part("area_type") RequestBody areaType,
            @Part("ste_type") RequestBody steType
    );

    /**
     * 补贴
     */
    //多查
    @GET("Api/Compensate/index")
    Call<Result<ArrayList<Compensate>>> getCompensates(@Query("user_id") String userId);

    //单查
    @GET("Api/Compensate/find")
    Call<Result<Compensate>> getCompensate(@Query("id") String id);

    //增
    @Multipart
    @POST("Api/Compensate/add")
    Call<Result<Compensate>> addCompensate(
            @Part("user_id") RequestBody userId,
            @Part("title") RequestBody title,
            @Part("price") RequestBody price,
            @Part("addtime") RequestBody addtime
    );

    //删
    @GET("Api/Compensate/DelCar")
    Call<Result<String>> delCompensate(@Query("id") String id);

    //改
    @Multipart
    @POST("Api/Compensate/editis")
    Call<Result<Compensate>> editCompensate(
            @Part("user_id") RequestBody userId,
            @Part("id") RequestBody id,
            @Part("title") RequestBody title,
            @Part("price") RequestBody price,
            @Part("addtime") RequestBody addtime
    );


    /**
     *
     */
    //收入支出主面板
    @GET("Api/Twophasecount/incomeexpenditure")
    Call<Result<InOut>> countInOut(
            @Query("user_id") String userId,
            @Query("typetime") String typeTime,
            @Query("starttime") String startTime,
            @Query("endtime") String endTime,
            @Query("time") String time
    );


    //草原面积主面板
    @GET("Api/Twophasecount/countPlantsum")
    Call<Result<Plant>> countPlantsum(
            @Query("user_id") String userId,
            @Query("typetime") String typeTime,
            @Query("starttime") String startTime,
            @Query("endtime") String endTime,
            @Query("time") String time
    );

    //补偿主面板
    @GET("Api/Twophasecount/countCompensate")
    Call<Result<ArrayList<Compensate>>> countCompensate(
            @Query("user_id") String userId,
            @Query("typetime") String typeTime,
            @Query("starttime") String startTime,
            @Query("endtime") String endTime,
            @Query("time") String time
    );

    /*
     * 三级地址
     */
    //省份
    @GET("Api/Diqu/index")
    Call<Result<ArrayList<Region>>> getProvinces();

    //市
    @GET("Api/Diqu/indexpid")
    Call<Result<ArrayList<Region>>> getCities(@Query("pid") String pid);

    //县
    @GET("Api/Diqu/indexpid")
    Call<Result<ArrayList<Region>>> getCounty(@Query("pid") String pfid);


    /**
     * 图片上传
     */
    @Multipart
    @POST("Api/Img/tupian")
    Call<Result<ImgResult>> uploadImg(@Part MultipartBody.Part file);

    /**
     * 获取养殖分类
     */
    @GET("Api/Public/breedclass")
    Call<Result<BreedClassResult>> getBreedClass();

    /**
     * 获取养殖档案列表
     */
    @GET("Api/Breedepidemic/index")
    Call<Result<BreedsResult>> getBreedList(@Query("user_id") String userId);

    /**
     * 养殖档案分类下的数据统计
     */
    @GET("Api/Breed/statistics")
    Call<Result<ArrayList<BreedsResult>>> getBreedStatistics(@Query("user_id") String userId);

    /**
     * 获取养殖档案牲畜id下的列表
     */
    @GET("Api/Breed/index")
    Call<Result<ArrayList<BreedIndexResult>>> getBreedIndex(@Query("user_id") String userId, @Query("breedclass_id") String breedClassId);

    /**
     * 提交养殖档案
     *
     * @param userId
     * @param breedclass_id
     * @param number
     * @param acquisition_time
     * @param become_time
     * @param title
     * @param age
     * @param price
     * @param img
     * @return
     */

    @GET("Api/Breed/add")
    Call<Result<BreedsResult>> enrollbreed(@Query("user_id") String userId,
                                           @Query("breedclass_id") String breedclass_id,
                                           @Query("number") String number,
                                           @Query("acquisition_time") String acquisition_time,
                                           @Query("become_time") String become_time,
                                           @Query("title") String title,
                                           @Query("age") String age,
                                           @Query("price") String price,
                                           @Query("img") String img,
                                           @Query("sheng") String sheng,
                                           @Query("shi") String shi,
                                           @Query("xian") String xian,
                                           @Query("supplier_id") String supplier_id,
                                           @Query("variety_id") String type

    );

    @GET("Api/ApiUser/info")
    Call<Result<UserInfoResult>> getUserInfo(@Query("user_id") String userId);


    @GET("Api/Breeddata/indexlist")
    Call<Result<LinkedTreeMap>> getBreedData(
            @Query("user_id") String userId,
            @Query("breed_id") String breedId,
//            指定类型
            @Query("type") String type,
//          指定类型下的参数
            @Query("parameter") String param
    );

    @GET("Api/Breed/find")
    Call<Result<BreedBaseMsg>> getBreedFind(
            @Query("id") String id,
            @Query("user_id") String user_id);


    //牲畜种类
    @GET("Api/Public/variety")
    Call<Result<ArrayList<Variety>>> getVars();

    //供应商种类
    @GET("Api/Public/supplier")
    Call<Result<ArrayList<Supplier>>> getSuppliers();

    /**
     * 获取新闻列表
     * @param p
     * @return
     */
    @GET("Api/News/newslist")
    Call<Result<ArrayList<FindResult>>> getFind(@Query("p") String p);

    /**
     * 获取新闻详情页
     * @param id
     * @return
     */
    @GET("Api/News/find")
    Call<Result<ArrayList<FindDetailResult>>> getFindDetail(@Query("id") String id);

    /**
     * 个人信息修改
     */
    @Multipart
    @POST("Api/User/infor")
    Call<Result<UserInfoResult>> editUserInfo(@Part("user_id") RequestBody userId,
                                              @Part("username") RequestBody username,
                                              @Part("password") RequestBody password,
                                              @Part("email") RequestBody email,
                                              @Part("phone") RequestBody phone,
                                              @Part("sex") RequestBody sex,
                                              @Part("address") RequestBody address,
                                              @Part("id_card") RequestBody idCard,
                                              @Part("age") RequestBody age,
                                              @Part("sheng") RequestBody sheng,
                                              @Part("shi") RequestBody shi,
                                              @Part("xian") RequestBody xian,
                                              @Part("avatar") RequestBody avatar
    );
}