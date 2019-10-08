package com.ais.brm.common.domain;

/**
 * 分页对象
 *
 * @author zhuqz
 */
public class Page {

    // 当前页面上的分页起始页码
    private int currentPageBeginNum = 1;

    // 当前页面上的分页结束页码
    private int currentPageEndNum = 6;

    // 当前页面上分页选中的页码
    private int currentPageNum = 1;

    // 记录总数
    private long totalRecords;

    // 总页数
    private int totalPages = 0;

    // 每页大小
    private int pageSize = 10;

    // 自定义总页数
    private int maxPageNum = 0;

    // private static final int DEFAULT_PAGESIZE = 10;

    // private static final int DEFAULT_CURRENT_PAGE_COUNT = 6;

    public int getMaxPageNum() {
        return maxPageNum;
    }

    public void setMaxPageNum(int maxPageNum) {
        this.maxPageNum = maxPageNum;
    }

    public Page() {
    }

    public Page(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * 在新建了页后，根据给出的总记录数自动设置页面的初始参数.
     *
     * @param totalRecords int 从数据库中查出的总记录数
     * @return Page 页
     */
    public Page autoInit(long totalRecords) {
        this.totalRecords = totalRecords;
        if (totalRecords > 0) {
            if (maxPageNum > 0) {
                this.pageSize = maxPageNum;
                if (totalRecords % this.pageSize == 0) {
                    this.totalPages = (int) (totalRecords / this.pageSize);
                } else {
                    this.totalPages = (int) (totalRecords / this.pageSize + 1);
                }
            } else {
                if (totalRecords % this.pageSize == 0) {
                    this.totalPages = (int) (totalRecords / this.pageSize);
                } else {
                    this.totalPages = (int) (totalRecords / this.pageSize + 1);
                }
            }


            if (totalPages < 6) {
                return this;
            }

            // if(this.currentPageNum > 0)
            // {
            // 如果当前页码大于总页码
            if (totalPages < this.currentPageNum && totalPages > 6) {
                // 将当前页面设置为最后一页
                this.currentPageNum = totalPages;
                // 将页面上的结束页码设置为最后一页
                this.currentPageEndNum = totalPages;
                // 将页面上的开始页码设置为最后页码的前五页
                this.currentPageBeginNum = this.totalPages - 5;
            }

            // 用结束页面减去当前页码
            int ddNum = currentPageEndNum - currentPageNum;

            // 如果是小于0，则说明点击下一页或页面输入框填写了大于当前页面结束页面的值
            if (ddNum <= 0) {
                // 如果当前页码加2大于总页数，则说明页面显示到了最后一个范围
                if (this.currentPageNum + 2 > totalPages) {
                    this.currentPageBeginNum = this.totalPages - 5;
                    this.currentPageEndNum = totalPages;
                } else // 起始页码为当前页码+2，结束页面为当前页码减3
                {
                    this.currentPageEndNum = this.currentPageNum + 2;
                    this.currentPageBeginNum = this.currentPageNum - 3;
                }
            } else if (ddNum > 5) // 则说明是在跳转输入框输入了小于当前页面起始值的页码
            {
                // 如果减3小于0则说明已经到了最前面的6页面了，则把页码起始值设置为1，页码结束值设置为6
                if (this.currentPageNum - 3 < 0) {
                    this.currentPageBeginNum = 1;
                    this.currentPageEndNum = 6;
                } else // 否则页码起始值设置为当前页码减3，结束值设置为当前页码加2，以便于当前页码显示在中间位置
                {
                    this.currentPageEndNum = this.currentPageNum + 2;
                    this.currentPageBeginNum = this.currentPageNum - 3;
                }
            } else if (ddNum > 0 && ddNum < 3 && currentPageEndNum != totalPages) // 如果满足这个条件，说明页面起始值和结束值需要增加
            {
                this.currentPageBeginNum = this.currentPageBeginNum + (3 - ddNum);
                this.currentPageEndNum = this.currentPageEndNum + (3 - ddNum);
            } else if (ddNum <= 5 && ddNum > 3 && this.currentPageBeginNum != 1) // 如果满足该条件，说明页面起始值和结束值需要减少
            {
                // 当前页码减去当前页面的起始页码
                int ffNum = this.currentPageNum - this.currentPageBeginNum;

                // 如果差值等于0，则把差值设置为2，此时需要起始页码和终止页码需要向前移动2个
                if (ffNum == 0) {
                    ffNum = 2;
                }

                // 如果满足该条件，则出现如下情景。起始页码为2，结束页码为7，当前页码为4的情况下，只需要减1就可以了
                if (this.currentPageBeginNum - ffNum <= 0) {
                    ffNum = 1;
                }
                this.currentPageBeginNum = this.currentPageBeginNum - ffNum;
                this.currentPageEndNum = this.currentPageEndNum - ffNum;
            }
            // }
        }
        return this;
    }

    public int getCurrentPageBeginNum() {
        return currentPageBeginNum;
    }

    public void setCurrentPageBeginNum(int currentPageBeginNum) {
        this.currentPageBeginNum = currentPageBeginNum;
    }

    public int getCurrentPageNum() {
        return currentPageNum;
    }

    public void setCurrentPageNum(int currentPageNum) {
        this.currentPageNum = currentPageNum;
    }

    public int getCurrentPageEndNum() {
        return currentPageEndNum;
    }

    public void setCurrentPageEndNum(int currentPageEndNum) {
        this.currentPageEndNum = currentPageEndNum;
    }

    public long getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(long totalRecords) {
        this.totalRecords = totalRecords;
    }

    public int getPageSize() {

//	if (pageSize > 50) {
//	    pageSize = 12;
//	} else if (0 == pageSize) {
//	    pageSize = 12;
//	}
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    @Override
    public String toString() {
        return "Page [currentPageBeginNum=" + currentPageBeginNum +
                ", currentPageEndNum="
                + currentPageEndNum
                + ", currentPageNum=" + currentPageNum + "," +
                " totalRecords=" + totalRecords + ", totalPages=" + totalPages
                + ", pageSize=" + pageSize + ", maxPageNum=" + maxPageNum + "]";
    }
}
