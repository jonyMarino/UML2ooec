/*
 * http://code.google.com/p/nativeclient/source/browse/trunk/src/native_client/src/shared/gio/gio.c
 */
int GioFileCtor(struct GioFile  *self,
                char const      *fname,
                char const      *mode) {
  self->base.vtbl = (struct GioVtbl *) NULL;
  self->iop = fopen(fname, mode);
  if (NULL == self->iop) {
    return 0;
  }
  self->base.vtbl = &kGioFileVtbl;
  return 1;
}
