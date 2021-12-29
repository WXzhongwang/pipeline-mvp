package com.rany.ops.common.alert;

/**
 * dingding 常量
 *
 * @author dick
 * @description dingding 常量
 * @date 2021/9/24 11:34 下午
 * @email 18668485565@163.com
 */

public final class DingConstants {

    private DingConstants() {
    }

    /**
     * msg type
     */
    public static final String DING_MSG_TYPE_TEXT = "text";
    public static final String DING_MSG_TYPE_LINK = "link";
    public static final String DING_MSG_TYPE_MARKDOWN = "markdown";
    public static final String DING_MSG_TYPE_ACTION_CARD = "actionCard";
    public static final String DING_MSG_TYPE_FEED_CARD = "feedCard";

    public static final String DING_REQ_KEY_MSG_TYPE = "msgtype";

    public static final String DING_REQ_KEY_TEXT = "text";
    public static final String DING_REQ_KEY_LINK = "link";
    public static final String DING_REQ_KEY_MARKDOWN = "markdown";
    public static final String DING_REQ_KEY_ACTION_CARD = "actionCard";
    public static final String DING_REQ_KEY_FEED_CARD = "feedCard";

    /**
     * text
     */
    public static final String TEXT_REQ_KEY_CONTENT = "content";
    public static final String TEXT_REQ_KEY_AT = "at";
    public static final String TEXT_REQ_KEY_AT_MOBILES = "atMobiles";
    public static final String TEXT_REQ_KEY_AT_ALL = "isAtAll";

    /**
     * link
     */
    public static final String LINK_REQ_KEY_TITLE = "title";
    public static final String LINK_REQ_KEY_PIC_URL = "picURL";
    public static final String LINK_REQ_KEY_MESSAGE_URL = "messageUrl";

    /**
     * markdown
     */
    public static final String MARKDOWN_REQ_KEY_TITLE = "title";
    public static final String MARKDOWN_REQ_KEY_TEXT = "text";
    public static final String MARKDOWN_REQ_KEY_AT = "at";
    public static final String MARKDOWN_REQ_KEY_AT_MOBILES = "atMobiles";
    public static final String MARKDOWN_REQ_KEY_AT_ALL = "isAtAll";


    /**
     * action card
     */
    public static final String ACTION_CARD_REQ_KEY_TITLE = "title";
    public static final String ACTION_CARD_REQ_KEY_TEXT = "text";
    public static final String ACTION_CARD_REQ_KEY_HIDE_AVATAR = "hideAvatar";
    public static final String ACTION_CARD_REQ_KEY_BTN_ORIENTATION = "btnOrientation";
    public static final String ACTION_CARD_REQ_KEY_SINGLE_TITLE = "singleTitle";
    public static final String ACTION_CARD_REQ_KEY_SINGLE_URL = "singleURL";
    public static final String ACTION_CARD_REQ_KEY_BTN = "btns";
    public static final String ACTION_CARD_REQ_KEY_BTN_TITLE = "title";
    public static final String ACTION_CARD_REQ_KEY_BTN_ACTION_URL = "actionURL";


    /**
     * feed card
     */
    public static final String FEED_CARD_REQ_KEY_LINKS = "links";
    public static final String FEED_CARD_REQ_KEY_TITLE = "title";
    public static final String FEED_CARD_REQ_KEY_MESSAGE_URL = "messageURL";
    public static final String FEED_CARD_REQ_KEY_PIC_URL = "picURL";

}
