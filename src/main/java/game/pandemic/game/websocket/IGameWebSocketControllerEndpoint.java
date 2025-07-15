package game.pandemic.game.websocket;

import game.pandemic.lobby.member.UserLobbyMember;
import game.pandemic.websocket.endpoint.IWebSocketControllerEndpoint;

public interface IGameWebSocketControllerEndpoint extends IWebSocketControllerEndpoint<UserLobbyMember> {
}
