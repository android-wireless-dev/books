package com.androidbook.simpleopengl;

import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.egl.EGLSurface;
import javax.microedition.khronos.opengles.GL10;

import android.app.Activity;
import android.content.Context;
import android.opengl.GLDebugHelper;
import android.opengl.GLU;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class ShowGLLightingActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAndroidSurface = new BasicGLSurfaceView(this);

        setContentView(mAndroidSurface);
    }

    private class BasicGLSurfaceView extends SurfaceView implements
            SurfaceHolder.Callback {
        SurfaceHolder mAndroidHolder;

        BasicGLSurfaceView(Context context) {
            super(context);
            mAndroidHolder = getHolder();
            mAndroidHolder.addCallback(this);
            mAndroidHolder.setType(SurfaceHolder.SURFACE_TYPE_GPU);

        }

        public void surfaceChanged(SurfaceHolder holder, int format, int width,
                int height) {
        }

        public void surfaceCreated(SurfaceHolder holder) {
            mGLThread = new BasicGLThread(this);

            mGLThread.start();
        }

        public void surfaceDestroyed(SurfaceHolder holder) {
            if (mGLThread != null) {
                mGLThread.requestStop();
            }
        }
    }

    BasicGLThread mGLThread;

    private class BasicGLThread extends Thread {
        private static final String DEBUG_TAG = "ShowGLLightingActivity$BasicGLThread";
        SurfaceView sv;

        BasicGLThread(SurfaceView view) {
            sv = view;
        }

        private boolean mDone = false;

        public void run() {
            try {
                initEGL();
                initGL();

                CubeSmallGLUT cube = new CubeSmallGLUT(3);
                mGL.glMatrixMode(GL10.GL_MODELVIEW);
                mGL.glLoadIdentity();
                GLU.gluLookAt(mGL, 0, 0, 8f, 0, 0, 0, 0, 1, 0f);
                while (!mDone) {
                    mGL.glClear(GL10.GL_COLOR_BUFFER_BIT
                            | GL10.GL_DEPTH_BUFFER_BIT);
                    mGL.glRotatef(1f, 1f, 1f, 1f);
                    mGL.glColor4f(1f, 0f, 0f, 1f);

                    cube.draw(mGL);

                    mGL.glFlush();
                    mEGL.eglSwapBuffers(mGLDisplay, mGLSurface);
                }
            } catch (Exception e) {
                Log.e(DEBUG_TAG, "GL Failure", e);
            } finally {
                cleanupGL();
            }
        }

        public void requestStop() {
            mDone = true;
            try {
                join();
            } catch (InterruptedException e) {
                Log.e(DEBUG_TAG, "failed to stop gl thread", e);
            }

            cleanupGL();
        }

        private void cleanupGL() {
            if (mEGL != null) {
                mEGL.eglMakeCurrent(mGLDisplay, EGL10.EGL_NO_SURFACE,
                        EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_CONTEXT);
                mEGL.eglDestroySurface(mGLDisplay, mGLSurface);
                mEGL.eglDestroyContext(mGLDisplay, mGLContext);
                mEGL.eglTerminate(mGLDisplay);
                mEGL = null;
            }

            Log.i(DEBUG_TAG, "GL Cleaned up");
        }

        public void initGL() {
            int width = sv.getWidth();
            int height = sv.getHeight();
            mGL.glViewport(0, 0, width, height);
            mGL.glMatrixMode(GL10.GL_PROJECTION);
            mGL.glLoadIdentity();
            float aspect = (float) width / height;
            GLU.gluPerspective(mGL, 45.0f, aspect, 1.0f, 30.0f);
            mGL.glClearColor(0.5f, 0.5f, 0.5f, 1);
            mGL.glClearDepthf(1.0f);

            // light
            mGL.glEnable(GL10.GL_LIGHTING);

            // the first light
            mGL.glEnable(GL10.GL_LIGHT0);

            // ambient values
            mGL.glLightfv(GL10.GL_LIGHT0, GL10.GL_AMBIENT, new float[] { 0.1f,
                    0.1f, 0.1f, 1f }, 0);

            // light that reflects in all directions
            mGL.glLightfv(GL10.GL_LIGHT0, GL10.GL_DIFFUSE, new float[] { 1f,
                    1f, 1f, 1f }, 0);

            // place it in projection space
            mGL.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, new float[] { 10f,
                    0f, 10f, 1 }, 0);

            // allow our object colors to create the diffuse/ambient material
            // setting
            mGL.glEnable(GL10.GL_COLOR_MATERIAL);

            // some rendering options
            mGL.glShadeModel(GL10.GL_SMOOTH);

            mGL.glEnable(GL10.GL_DEPTH_TEST);
            // mGL.glDepthFunc(GL10.GL_LEQUAL);
            mGL.glEnable(GL10.GL_CULL_FACE);

            mGL.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);

            // the only way to draw primitives with OpenGL ES
            mGL.glEnableClientState(GL10.GL_VERTEX_ARRAY);

            Log.i(DEBUG_TAG, "GL initialized");
        }

        public void initEGL() throws Exception {
            mEGL = (EGL10) GLDebugHelper.wrap(EGLContext.getEGL(),
                    GLDebugHelper.CONFIG_CHECK_GL_ERROR
                            | GLDebugHelper.CONFIG_CHECK_THREAD, null);

            if (mEGL == null) {
                throw new Exception("Couldn't get EGL");
            }

            mGLDisplay = mEGL.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);

            if (mGLDisplay == null) {
                throw new Exception("Couldn't get display for GL");
            }

            int[] curGLVersion = new int[2];
            mEGL.eglInitialize(mGLDisplay, curGLVersion);

            Log.i(DEBUG_TAG, "GL version = " + curGLVersion[0] + "."
                    + curGLVersion[1]);

            EGLConfig[] configs = new EGLConfig[1];
            int[] num_config = new int[1];
            mEGL.eglChooseConfig(mGLDisplay, mConfigSpec, configs, 1,
                    num_config);
            mGLConfig = configs[0];

            mGLSurface = mEGL.eglCreateWindowSurface(mGLDisplay, mGLConfig,
                    sv.getHolder(), null);

            if (mGLSurface == null) {
                throw new Exception("Couldn't create new surface");
            }

            mGLContext = mEGL.eglCreateContext(mGLDisplay, mGLConfig,
                    EGL10.EGL_NO_CONTEXT, null);

            if (mGLContext == null) {
                throw new Exception("Couldn't create new context");
            }

            if (!mEGL.eglMakeCurrent(mGLDisplay, mGLSurface, mGLSurface,
                    mGLContext)) {
                throw new Exception("Failed to eglMakeCurrent");
            }

            mGL = (GL10) GLDebugHelper.wrap(mGLContext.getGL(),
                    GLDebugHelper.CONFIG_CHECK_GL_ERROR
                            | GLDebugHelper.CONFIG_CHECK_THREAD
                            | GLDebugHelper.CONFIG_LOG_ARGUMENT_NAMES, null);

            if (mGL == null) {
                throw new Exception("Failed to get GL");
            }

        }

        // main OpenGL variables
        GL10 mGL;
        EGL10 mEGL;
        EGLDisplay mGLDisplay;
        EGLConfig mGLConfig;
        EGLSurface mGLSurface;
        EGLContext mGLContext;
        int[] mConfigSpec = { EGL10.EGL_RED_SIZE, 5, EGL10.EGL_GREEN_SIZE, 6,
                EGL10.EGL_BLUE_SIZE, 5, EGL10.EGL_DEPTH_SIZE, 16,
                EGL10.EGL_NONE };
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    SurfaceView mAndroidSurface;
}
