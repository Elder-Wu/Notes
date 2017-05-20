package com.wuzhanglao.niubi.test;

/**
 * Created by ming.wu@shanbay.com on 2017/5/10.
 */

public class ComposeMessage<T extends NormalMessage,V extends ReplyMessage> {
	private T normalMsg;
	private V replyMsg;

	public ComposeMessage(T normalMsg, V replyMsg) {
		this.normalMsg = normalMsg;
		this.replyMsg = replyMsg;

	}

	public void render(){
		normalMsg.render();
		replyMsg.render();
	}


}
