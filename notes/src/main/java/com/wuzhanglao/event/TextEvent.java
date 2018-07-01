package com.wuzhanglao.niubi.event;

/**
 * Created by ming.wu@shanbay.com on 2017/4/19.
 */

public class TextEvent extends BaseEvent {
	private String msg;

	public TextEvent(String msg) {
		this.msg = msg;
	}
}