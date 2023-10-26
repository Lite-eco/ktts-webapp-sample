import { Errors } from '../errors';
import { UserInfos, UserStatus } from '../generated/domain/User.generated';
import { create } from 'zustand';

interface UserState {
  userInfos?: UserInfos;
  setUserInfos: (userInfos: UserInfos) => void;
  updateUserStatus: (status: UserStatus) => void;
}

export const useUserState = create<UserState>(set => ({
  userInfos: bootstrapData.userInfos,
  setUserInfos: (userInfos: UserInfos) => set({ userInfos }),
  updateUserStatus: (status: UserStatus) =>
    set(state => {
      if (!state.userInfos) {
        throw Errors._290c5193();
      }
      return { userInfos: { ...state.userInfos, status } };
    })
}));
