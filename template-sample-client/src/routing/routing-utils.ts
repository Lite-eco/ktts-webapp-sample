import { getValue } from '../utils/nominal-class';
import { ApplicationRoute } from './ApplicationRoute.generated';
import { routePathMap } from './routePathMap.generated';
import {
  useLocation,
  useMatches,
  useNavigate,
  useParams
} from 'react-router-dom';

export const useTypedParams = <T extends ApplicationRoute>(): Omit<T, 'name'> =>
  useParams() as Omit<T, 'name'>;

export const useTypedMatches = () => {
  const matches = useMatches();
  return matches.map(m => ({
    ...m,
    id: m.id as ApplicationRoute['name']
  }));
};

export const buildPath = (route: ApplicationRoute) => {
  let path = getValue(routePathMap, route.name);
  Object.keys(route)
    .filter(k => k !== 'name')
    .forEach((k: string) => {
      // @ts-ignore
      const param = route[k];
      if (path.indexOf(k) === -1) {
        throw Error(`Missing parameter ${k} in ${path}.`);
      }
      path = path.replace(':' + k, param);
    });
  return path;
};

export const useGoTo = () => {
  const navigate = useNavigate();
  const location = useLocation();
  return (
    route: ApplicationRoute,
    options?: {
      replace?: boolean;
      targetPath?: string;
      useTargetPath?: boolean;
    }
  ) => {
    if (options?.targetPath && options?.useTargetPath) {
      throw Error();
    }
    if (options?.useTargetPath) {
      const targetPath =
        (location.state as any | undefined)?.targetPath ?? buildPath(route);
      navigate(targetPath, {
        replace: options.replace ?? false,
        state: undefined
      });
    } else {
      navigate(buildPath(route), {
        replace: options?.replace ?? false,
        state: options?.targetPath
          ? { targetPath: options.targetPath }
          : undefined
      });
    }
  };
};
