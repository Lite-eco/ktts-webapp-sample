/** @jsxImportSource @emotion/react */
import { LogoutButton } from '../../../common-components/form/LogoutButton';
import { useI18n } from '../../../hooks/i18n';
import { RouteLink } from '../../../routing/RouteLink';
import { state } from '../../../state/state';
import { colors } from '../../../styles/vars';
import { NavbarI18n } from './Navbar.i18n';
import { css } from '@emotion/react';
import { Menu as MenuIcon } from '@mui/icons-material';
import { Menu, MenuItem } from '@mui/material';
import IconButton from '@mui/material/IconButton';
import { useRef, useState } from 'react';
import { useRecoilValue } from 'recoil';

export const Navbar = () => {
  const userInfos = useRecoilValue(state.userInfos);
  const buttonElement = useRef<HTMLButtonElement>(null);
  const [open, setOpen] = useState(false);
  const close = () => setOpen(false);
  const t = useI18n(NavbarI18n);
  return (
    <div
      css={css`
        display: flex;
        justify-content: space-between;
        width: 100%;
        height: 36px;
        padding: 10px 20px;
        background: ${colors.grey};
      `}
    >
      <RouteLink
        addCss={css`
          color: ${colors.white};
          text-decoration: none;
          text-transform: uppercase;
        `}
        route={{
          name: 'Root'
        }}
      >
        {t.TemplateSample()}
      </RouteLink>
      <div
        css={css`
          display: flex;
        `}
      >
        <IconButton
          onClick={() => setOpen(!open)}
          ref={buttonElement}
          css={css`
            color: ${colors.white};
          `}
        >
          <MenuIcon />
        </IconButton>
        <Menu anchorEl={buttonElement.current} open={open} onClose={close}>
          {!userInfos && (
            <MenuItem>
              <RouteLink
                route={{
                  name: 'Login'
                }}
              >
                {t.Login()}
              </RouteLink>
            </MenuItem>
          )}
          {!userInfos && (
            <MenuItem>
              <RouteLink
                route={{
                  name: 'Register'
                }}
              >
                {t.Register()}
              </RouteLink>
            </MenuItem>
          )}
          {userInfos?.status === 'Active' && (
            <MenuItem>
              <RouteLink
                route={{
                  name: 'Account'
                }}
              >
                {t.Account()}
              </RouteLink>
            </MenuItem>
          )}
          {userInfos?.role === 'Admin' && (
            <MenuItem>
              <RouteLink
                route={{
                  name: 'Admin/UsersManagement'
                }}
              >
                {t.UsersManagement()}
              </RouteLink>
            </MenuItem>
          )}
          {userInfos?.role === 'Admin' && (
            <MenuItem>
              <RouteLink
                route={{
                  name: 'Admin/ManualCommand'
                }}
              >
                {t.ManualCommands()}
              </RouteLink>
            </MenuItem>
          )}
          {userInfos?.role === 'Admin' && (
            <MenuItem>
              <RouteLink
                route={{
                  name: 'Admin/RouteListing'
                }}
              >
                {t.RoutesListing()}
              </RouteLink>
            </MenuItem>
          )}
          {userInfos && (
            <MenuItem>
              <LogoutButton />
            </MenuItem>
          )}
        </Menu>
      </div>
    </div>
  );
};
