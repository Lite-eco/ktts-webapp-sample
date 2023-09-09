import { UserId } from '../domain/Ids.generated';
import { AdminUserInfos, UserStatus } from '../domain/User.generated';

export type Query =
  | AdminGetUserInfosQuery
  | AdminGetUsersQuery
  | GetUserStatusQuery
  | IsMailAlreadyTakenQuery;

export type QueryResponse =
  | AdminGetUserInfosQueryResponse
  | AdminGetUsersQueryResponse
  | GetUserStatusQueryResponse
  | IsMailAlreadyTakenQueryResponse;

export interface AdminGetUserInfosQuery {
  objectType: 'AdminGetUserInfosQuery';
  userId: UserId;
}

export interface AdminGetUserInfosQueryResponse {
  objectType: 'AdminGetUserInfosQueryResponse';
  userInfos?: AdminUserInfos;
}

export interface AdminGetUsersQuery {
  objectType: 'AdminGetUsersQuery';
}

export interface AdminGetUsersQueryResponse {
  objectType: 'AdminGetUsersQueryResponse';
  users: AdminUserInfos[];
}

export interface GetUserStatusQuery {
  objectType: 'GetUserStatusQuery';
}

export interface GetUserStatusQueryResponse {
  objectType: 'GetUserStatusQueryResponse';
  status: UserStatus;
}

export interface IsMailAlreadyTakenQuery {
  objectType: 'IsMailAlreadyTakenQuery';
  mail: string;
}

export interface IsMailAlreadyTakenQueryResponse {
  objectType: 'IsMailAlreadyTakenQueryResponse';
  alreadyTaken: boolean;
}
