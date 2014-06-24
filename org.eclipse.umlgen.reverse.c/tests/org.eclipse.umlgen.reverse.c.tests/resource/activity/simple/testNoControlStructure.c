/*
 * http://code.google.com/p/nativeclient/source/browse/trunk/src/native_client/src/shared/gio/gio.c
 */
off_t GioFileSeek(struct Gio  *vself,
                  off_t       offset,
                  int         whence) {
  struct GioFile  *self = (struct GioFile *) vself;
  int             ret;

  ret = fseek(self->iop, (long) offset, whence);

  return (off_t) ftell(self->iop);
}
