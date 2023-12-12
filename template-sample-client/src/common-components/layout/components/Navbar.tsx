/** @jsxImportSource @emotion/react */
import { ApplicationPath } from '../../../generated/routing/ApplicationPath.generated';
import { useI18n } from '../../../hooks/i18n';
import { useUserState } from '../../../state/UserState';
import { colors } from '../../../style/vars';
import { buildPath } from '../../../utils/routing-utils';
import { RouteLink } from '../../RouteLink';
import { LogoutMenuItem } from './LogoutMenuItem';
import { NavbarI18n } from './Navbar.i18n';
import { css } from '@emotion/react';
import { Menu as MenuIcon } from '@mui/icons-material';
import { ListItemButton, ListItemText, Menu, MenuItem } from '@mui/material';
import IconButton from '@mui/material/IconButton';
import { PropsWithChildren, useRef, useState } from 'react';
import { Link as RouterLink, useMatches } from 'react-router-dom';

export const Navbar = () => {
  const userInfos = useUserState(s => s.userInfos);
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
        path={{
          name: 'Root'
        }}
        addCss={css`
          color: ${colors.white};
          text-decoration: none;
          text-transform: uppercase;
        `}
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
            <MenuLink
              path={{
                name: 'Login'
              }}
              label={t.Login()}
            />
          )}
          {!userInfos && (
            <MenuLink
              path={{
                name: 'Register'
              }}
              label={t.Register()}
            />
          )}
          {userInfos?.status === 'Active' && (
            <MenuLink
              path={{
                name: 'Account'
              }}
              label={t.Account()}
            />
          )}
          {userInfos?.role === 'Admin' && (
            <MenuLink
              path={{
                name: 'Admin'
              }}
              label={t.Admin()}
            />
          )}
          {userInfos && <LogoutMenuItem />}
        </Menu>
      </div>
    </div>
  );
};

const MenuLink = (props: { path: ApplicationPath; label: string }) => {
  return (
    <MenuItem component={MatchRouterLink} path={props.path}>
      <ListItemButton>
        <ListItemText primary={props.label} />
      </ListItemButton>
    </MenuItem>
  );
};

const MatchRouterLink = (
  props: PropsWithChildren<{ path: ApplicationPath }>
) => {
  const matches = useMatches();
  const to = buildPath(props.path);
  const doesMatch = matches[matches.length - 1].pathname === to;
  return (
    <div
      css={css`
        background: ${doesMatch ? colors.clearGrey : 'white'};
      `}
    >
      <RouterLink
        to={to}
        css={css`
          color: ${colors.grey};
          text-decoration: none;
        `}
      >
        {props.children}
      </RouterLink>
    </div>
  );
};
