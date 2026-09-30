package com.zyyyl.system.service.impl;

import static com.zyyyl.system.domain.table.SysPostTableDef.*;

import java.util.List;

import org.springframework.stereotype.Service;

import com.zyyyl.common.core.domain.entity.SysUser;
import com.zyyyl.common.exception.ServiceException;
import com.zyyyl.common.utils.poi.ExcelUtil;
import com.zyyyl.system.domain.SysPost;
import com.zyyyl.system.domain.SysUserPost;
import com.zyyyl.system.mapper.SysPostMapper;
import com.zyyyl.system.service.ISysPostService;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryChain;
import com.mybatisflex.spring.service.impl.ServiceImpl;

import jakarta.servlet.http.HttpServletResponse;

/**
 * 岗位信息 服务层处理
 * 
 * @author geek
 */
@Service
public class SysPostServiceImpl extends ServiceImpl<SysPostMapper, SysPost> implements ISysPostService {
    /**
     * 查询岗位信息集合
     * 
     * @param post 岗位信息
     * @return 岗位信息集合
     */
    private QueryChain<SysPost> selectPostList(SysPost post) {
        return this.queryChain()
                .like(SysPost::getPostCode, post.getPostCode())
                .eq(SysPost::getStatus, post.getStatus())
                .like(SysPost::getPostName, post.getPostName());
    }

    @Override
    public Page<SysPost> page(SysPost post, Integer pageNum, Integer pageSize) {
        return selectPostList(post).page(Page.of(pageNum, pageSize));
    }

    @Override
    public void export(SysPost post, HttpServletResponse response) {
        List<SysPost> list = selectPostList(post).list();
        ExcelUtil<SysPost> util = new ExcelUtil<>(SysPost.class);
        util.exportExcel(response, list, "岗位数据");
    }

    /**
     * 根据用户ID获取岗位选择框列表
     * 
     * @param userId 用户ID
     * @return 选中岗位ID列表
     */
    @Override
    public List<Long> selectPostListByUserId(Long userId) {
        return this.queryChain()
                .select(SYS_POST.POST_ID)
                .leftJoin(SysUserPost.class)
                .on(SysPost::getPostId, SysUserPost::getPostId)
                .leftJoin(SysUser.class)
                .on(SysUser::getUserId, SysUserPost::getUserId)
                .eq(SysUser::getUserId, userId)
                .listAs(Long.class);
    }

    /**
     * 校验岗位名称是否唯一
     * 
     * @param post 岗位信息
     * @return 结果
     */
    @Override
    public boolean checkPostNameUnique(SysPost post) {
        return !this.queryChain()
                .eq(SysPost::getPostName, post.getPostName())
                .ne(SysPost::getPostId, post.getPostId())
                .exists();
    }

    /**
     * 校验岗位编码是否唯一
     * 
     * @param post 岗位信息
     * @return 结果
     */
    @Override
    public boolean checkPostCodeUnique(SysPost post) {
        return !this.queryChain()
                .eq(SysPost::getPostCode, post.getPostCode())
                .ne(SysPost::getPostId, post.getPostId())
                .exists();
    }

    /**
     * 通过岗位ID查询岗位使用数量
     * 
     * @param postId 岗位ID
     * @return 结果
     */
    @Override
    public long countUserPostById(Long postId) {
        return QueryChain.of(SysUserPost.class)
                .eq(SysUserPost::getPostId, postId)
                .count();
    }

    /**
     * 批量删除岗位信息
     * 
     * @param postIds 需要删除的岗位ID
     * @return 结果
     */
    @Override
    public boolean deletePostByIds(List<Long> postIds) {
        for (Long postId : postIds) {
            SysPost post = getById(postId);
            if (countUserPostById(postId) > 0) {
                throw new ServiceException("%1$s已分配,不能删除".formatted(post.getPostName()));
            }
        }
        return super.removeByIds(postIds);
    }
}
