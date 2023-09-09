import { UserId } from '../domain/Ids.generated';
import { UserInfos, UserStatus } from '../domain/User.generated';

export type Query =
  | GetUserInfosQuery
  | GetUserStatusQuery
  | GetUsersQuery
  | IsMailAlreadyTakenQuery;

export type QueryResponse =
  | GetUserInfosQueryResponse
  | GetUserStatusQueryResponse
  | GetUsersQueryResponse
  | IsMailAlreadyTakenQueryResponse;

export interface GetUserInfosQuery {
  objectType: 'GetUserInfosQuery';
  userId: UserId;
}

export interface GetUserInfosQueryResponse {
  objectType: 'GetUserInfosQueryResponse';
  userInfos?: UserInfos;
}

export interface GetUsersQuery {
  objectType: 'GetUsersQuery';
}

export interface GetUsersQueryResponse {
  objectType: 'GetUsersQueryResponse';
  users: UserInfos[];
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
