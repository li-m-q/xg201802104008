package cn.edu.sdjzu.xg.bysj.controller.basic.profTitle;

import cn.edu.sdjzu.xg.bysj.domain.ProfTitle;
import cn.edu.sdjzu.xg.bysj.service.ProfTitleService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import util.JSONUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;

/**
 * 将所有方法组织在一个Controller(Servlet)中
 */
@WebServlet("/profTitle.ctl")
public class ProfTitleController extends HttpServlet {
    /**
     * POST, http://localhost:8080/proftitle.ctl, 增加职称
     * 增加一个学院对象：将来自前端请求的JSON对象，增加到数据库表中
     *
     * @param request  请求对象
     * @param response 响应对象
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //根据request对象，获得代表参数的JSON字串
        String proftitle_json = JSONUtil.getJSON(request);

        //将JSON字串解析为Proftitle对象
        ProfTitle profTitleToAdd = JSON.parseObject(proftitle_json, ProfTitle.class);
        //前台没有为id赋值，此处模拟自动生成id。如果Dao能真正完成数据库操作，删除下一行。
        profTitleToAdd.setId(4 + (int) (Math.random() * 100));

        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        //在数据库表中增加School对象
        try {
            ProfTitleService.getInstance().add(profTitleToAdd);
            message.put("message", "增加成功");
        } catch (SQLException e) {
            message.put("message", "数据库操作异常");
            e.printStackTrace();
        } catch (Exception e) {
            message.put("message", "网络异常");
            e.printStackTrace();
        }
        //响应message到前端
        response.getWriter().println(message);
    }

    /**
     * DELETE, http://localhost:8080/proftitle.ctl?id=1, 删除id=1的职称
     * 删除一个对象：根据来自前端请求的id，删除数据库表中id的对应记录
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //读取参数id
        String id_str = request.getParameter("id");
        int id = Integer.parseInt(id_str);

        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();

        //到数据库表中删除对应的职称
        try {
            ProfTitleService.getInstance().delete(id);
            message.put("message", "删除成功");
        } catch (SQLException e) {
            message.put("message", "数据库操作异常");
            e.printStackTrace();
        } catch (Exception e) {
            message.put("message", "网络异常");
            e.printStackTrace();
        }
        //响应message到前端
        response.getWriter().println(message);
    }


    /**
     * PUT, http://localhost:8080/proftitle.ctl, 修改职称
     * <p>
     * 修改一个对象：将来自前端请求的JSON对象，更新数据库表中相同id的记录
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String proftitle_json = JSONUtil.getJSON(request);
        //将JSON字串解析为Proftitle对象
        ProfTitle profTitleToAdd = JSON.parseObject(proftitle_json, ProfTitle.class);

        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        //到数据库表修改Proftitle对象对应的记录
        try {
            ProfTitleService.getInstance().update(profTitleToAdd);
            message.put("message", "修改成功");
        } catch (SQLException e) {
            message.put("message", "数据库操作异常");
            e.printStackTrace();
        } catch (Exception e) {
            message.put("message", "网络异常");
            e.printStackTrace();
        }
        //响应message到前端
        response.getWriter().println(message);
    }

    /**
     * GET, http://localhost:8080/Proftitle.ctl?id=1, 查询id=1的职称
     * GET, http://localhost:8080/proftitle.ctl, 查询所有的职称
     * 把一个或所有职称对象响应到前端
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //读取参数id
        String id_str = request.getParameter("id");

        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        try {
            //如果id = null, 表示响应所有学院对象，否则响应id指定的学院对象
            if (id_str == null) {
                responseProftitles(response);
            } else {
                int id = Integer.parseInt(id_str);
                responseProftitle(id, response);
            }
        } catch (SQLException e) {
            message.put("message", "数据库操作异常");
            e.printStackTrace();
            //响应message到前端
            response.getWriter().println(message);
        } catch (Exception e) {
            message.put("message", "网络异常");
            e.printStackTrace();
            //响应message到前端
            response.getWriter().println(message);
        }
    }

    //响应一个职称对象
    private void responseProftitle(int id, HttpServletResponse response)
            throws ServletException, IOException, SQLException, ClassNotFoundException {
        //根据id查找
        ProfTitle profTitle = ProfTitleService.getInstance().find(id);
        String proftitle_json = JSON.toJSONString(profTitle);

        //响应proftitle_json到前端
        response.getWriter().println(proftitle_json);
    }

    //响应所有职称对象
    private void responseProftitles(HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        //获得所有职称
        Collection<ProfTitle> profTitles = ProfTitleService.getInstance().findAll();
        String proftitle_json = JSON.toJSONString(profTitles, SerializerFeature.DisableCircularReferenceDetect);

        //响应proftitle_json到前端
        response.getWriter().println(proftitle_json);
    }
}
