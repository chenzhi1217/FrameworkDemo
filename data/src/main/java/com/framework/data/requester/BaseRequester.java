package com.framework.data.requester;

/**
 * Created by chenzhi on 2017/11/19.
 */

public class BaseRequester {
    private int curPage = 0;
    private int pageSize = 15;

    /**
     * 下翻页
     */
    public void pgDown() {
        curPage++;
    }

    /**
     * 上翻页
     */
    public void pgUp() {
        curPage--;
    }

    /**
     * 设置每页的条目个数
     * @param pageSize
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * 重置页码
     */
    public void reSetPage() {
        this.curPage = 0;
    }

    /**
     * 获取每页的数量
     * @return
     */
    public int getPageSize() {
        return pageSize;
    }
}
