package com.mxleague.boot.service;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.List;

import com.mxleague.boot.domain.Board;
import com.mxleague.boot.domain.Player;
import com.mxleague.boot.domain.Role;
import com.mxleague.boot.domain.Statistic;
import com.mxleague.boot.domain.Transfer;
import com.mxleague.boot.domain.User;

public final class Link {

	protected static void updateRolePerUserOnceResourcewithLinks(Role role, List<Role> roles) {
		if (roles.contains(role)) {
			role.add(linkTo(methodOn(RoleRestController.class).getAll()).slash(role.getId_role()).withSelfRel());
			roles.remove(role);
		}
	}

	protected static void updateUsersPerRoleResourcewithLinks(Role role) {
		role.add(linkTo(methodOn(RoleRestController.class).getUsersByRole(role.getId_role())).withRel("users"));
	}

	protected static void updateRoleResourcewithLinks(Role role) {
		role.add(linkTo(methodOn(RoleRestController.class).getAll()).slash(role.getId_role()).withSelfRel());
	}

	protected static void updateUserResourcewithLinks(User user) {
		user.add(linkTo(methodOn(UserRestController.class).getAll()).slash(user.getId_user()).withSelfRel());
	}

	protected static void updatePlayerResourcewithLinks(Player player) {
		player.add(linkTo(methodOn(PlayerRestController.class).getAll()).slash(player.getId_player()).withSelfRel());
	}

	protected static void updateUserPerPlayerResourcewithLinks(User user) {
		user.add(linkTo(methodOn(PlayerRestController.class).getAll()).slash(user.getPlayer().getId_player())
				.slash(user.getId_user()).withSelfRel());
	}

	protected static void updateBoardResourcewithLinks(Board member) {
		member.add(linkTo(methodOn(BoardRestController.class).getAll()).slash(member.getId_board()).withSelfRel());
	}

	protected static void updateUserPerMemberOfBoardResourcewithLinks(User user) {
		user.add(linkTo(methodOn(BoardRestController.class).getAll()).slash(user.getBoard().getId_board())
				.slash(user.getId_user()).withSelfRel());
	}

	protected static void updateStatisticResourcewithLinks(Statistic statistic) {
		statistic.add(linkTo(methodOn(StatisticRestController.class).getAll()).slash(statistic.getId_statistic())
				.withSelfRel());
	}

	protected static void updatePlayerPerStatisticResourcewithLinks(Player player) {
		player.add(linkTo(methodOn(StatisticRestController.class).getAll())
				.slash(player.getStatistic().getId_statistic()).slash(player.getId_player()).withSelfRel());
	}

	protected static void updateTransferResourcewithLinks(Transfer transfer) {
		transfer.add(
				linkTo(methodOn(TransferRestController.class).getAll()).slash(transfer.getId_transfer()).withSelfRel());
	}

	protected static void updatePlayerPerTransferResourcewithLinks(Player player) {
		player.add(linkTo(methodOn(TransferRestController.class).getAll()).slash(player.getTransfer().getId_transfer())
				.slash(player.getId_player()).withSelfRel());
	}

}
