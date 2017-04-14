package org.forumj.network.web.servlet;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.forumj.common.FJServletName;
import org.forumj.common.FJUrl;
import org.forumj.common.config.FJConfiguration;
import org.forumj.network.web.controller.*;
import org.forumj.network.web.controller.post.*;
import org.forumj.network.web.filter.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static org.forumj.tool.Diletant.errorOut;

/**
 * Created by Andrew on 16/03/2017.
 */
@WebServlet(urlPatterns = {"/"}, name = FJServletName.INDEX)
public class Dispatcher extends HttpServlet {

    private Logger logger = LogManager.getLogger("org.forumj.web.filter");

    private String webappName;
    private String realPath = null;

    // GET controllers
    private Page404 page404 = new Page404();
    private Index pageGroupIndex = new Index();
    private Tema pageGroupThread = new Tema();
    private Images imagesController = new Images();
    private Mess newThreadController = new Mess();
    private Control settingsController = new Control();
    private Message messageController = new Message();
    private Auth loginController = new Auth();
    private Opr newQuestionController = new Opr();
    private Registration registrationController = new Registration();
    private ActivateUser confirmEmailController = new ActivateUser();
    private AddIgnor addIgnorController = new AddIgnor();
    private ApproveUser approveUserController = new ApproveUser();
    private Ban banController = new Ban();
    private CloseThread closeThreadController = new CloseThread();
    private DelOne moveToRecycleController = new DelOne();
    private Pin pinThreadController = new Pin();

    //POST controllers
    private Write addPostController = new Write();
    private New addThreadController = new New();
    private Submit doLoginController = new Submit();
    private Quest addQuestionController = new Quest();
    private AddSubscribe addSubscribeController = new AddSubscribe();
    private Amn updateIgnorController = new Amn();
    private Defview setDefaultViewController = new Defview();
    private DelFolder folderToolsController = new DelFolder();
    private DelMail deleteMailController = new DelMail();
    private DelOneSubs deleteOneSubscribeController = new DelOneSubs();
    private DelOneSubsByMail deleteOneSubscribeFromMailController = new DelOneSubsByMail();
    private DelSubs deleteSubscribesController = new DelSubs();
    private DelVFolder removeFolderFromViewController = new DelVFolder();
    private DelView deleteViewController = new DelView();
    private DelVoice deleteVoiceController = new DelVoice();
    private InsNew doRegistrationController = new InsNew();
    private MoveTitle moveThreadController = new MoveTitle();
    private NewFolder createFolderController = new NewFolder();
    private NewView createViewController = new NewView();
    private Ping pingController = new Ping();
    private Post commandHandler = new Post();
    private PostImage addImageController = new PostImage();
    private Send sendPrivateMessagController = new Send();
    private SetAvatar setAvatarController = new SetAvatar();
    private SetFooter setFooterController = new SetFooter();
    private SetLocation setLocationController = new SetLocation();
    private SlctView changeViewController = new SlctView();
    private UserVoice addCustomAnswerController = new UserVoice();
    private VAvatar setViewAvatarController = new VAvatar();
    private Voice addVoteController = new Voice();

    //Filters
    private FileUploadFilter fileUploadFilter = new FileUploadFilter();
    private ExitFilter exitFilter = new ExitFilter();
    private LoginFilter loginFilter = new LoginFilter();
    private LocaleResolver localeResolver = new LocaleResolver();
    private RestrictUnloginedUsersFilter restrictUnloginedUsersFilter = new RestrictUnloginedUsersFilter();


    @Override
    public void init() throws ServletException {
        super.init();
        try {
            realPath = getServletContext().getRealPath("/");
            imagesController.init(realPath);
            setAvatarController.init();
            fileUploadFilter.init();
            webappName = FJConfiguration.getAppName();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            request.setCharacterEncoding("UTF-8");
            String path = request.getRequestURI();
            // removing doubled slashes
            path = path.replace("//", "/");
            if (path != null && !path.isEmpty()) {
                String[] pathParts = path.split("/");
                String userURI = getUserURI(pathParts);
                if (isCorrectUserURI(userURI)) {
                    String controllerName = getControllerName(pathParts);
                    if (!webappName.isEmpty()){
                        userURI = webappName + "/" + userURI;
                    }
                    switch (controllerName){
                        case FJUrl.INDEX :
                            exitFilter.doFilter(request, response, webappName, userURI, controllerName, false, (req, resp, webapp, uri) -> {
                                loginFilter.doFilter(req, resp, webapp, uri, controllerName, (req1, resp1, webapp1, uri1) -> {
                                    localeResolver.doFilter(req1, resp1, webapp1, uri1, (req2, resp2, webapp2, uri2) -> {
                                        pageGroupIndex.doGet(req2, resp2, webapp2, uri2);
                                    });
                                });
                            });
                            break;
                        case FJUrl.VIEW_THREAD:
                            exitFilter.doFilter(request, response, webappName, userURI, controllerName, false, (req, resp, webapp, uri) -> {
                                loginFilter.doFilter(req, resp, webapp, uri, controllerName, (req1, resp1, webapp1, uri1) -> {
                                    localeResolver.doFilter(req1, resp1, webapp1, uri1, (req2, resp2, webapp2, uri2) -> {
                                        pageGroupThread.doGet(req2, resp2, webapp2, uri2);
                                    });
                                });
                            });
                        case FJUrl.VIEW_THREAD_OLD:
                            exitFilter.doFilter(request, response, webappName, userURI, FJUrl.VIEW_THREAD, false, (req, resp, webapp, uri) -> {
                                loginFilter.doFilter(req, resp, webapp, uri, FJUrl.VIEW_THREAD, (req1, resp1, webapp1, uri1) -> {
                                    localeResolver.doFilter(req1, resp1, webapp1, uri1, (req2, resp2, webapp2, uri2) -> {
                                        pageGroupThread.doGet(req2, resp2, webapp2, uri2);
                                    });
                                });
                            });
                            break;
                        case FJUrl.STATIC:
                        case FJUrl.PHOTO: // backward compatibility
                            loginFilter.doFilter(request, response, webappName, userURI, controllerName, (req, resp, webapp, uri) -> {
                                imagesController.doGet(req, resp);
                            });
                            break;
                        case FJUrl.NEW_THREAD:
                            exitFilter.doFilter(request, response, webappName, userURI, FJUrl.INDEX, true, (req, resp, webapp, uri) -> {
                                loginFilter.doFilter(req, resp, webapp, uri, FJUrl.VIEW_THREAD, (req1, resp1, webapp1, uri1) -> {
                                    localeResolver.doFilter(req1, resp1, webapp1, uri1, (req2, resp2, webapp2, uri2) -> {
                                        restrictUnloginedUsersFilter.doFilter(req2, resp2, webapp2, uri2, FJUrl.LOGIN, (req3, resp3, webapp3, uri3) -> {
                                            newThreadController.doGet(req3, resp3, webapp3, uri3);
                                        });
                                    });
                                });
                            });
                            break;
                        case FJUrl.SETTINGS:
                            exitFilter.doFilter(request, response, webappName, userURI, FJUrl.INDEX, true, (req, resp, webapp, uri) -> {
                                loginFilter.doFilter(req, resp, webapp, uri, FJUrl.VIEW_THREAD, (req1, resp1, webapp1, uri1) -> {
                                    localeResolver.doFilter(req1, resp1, webapp1, uri1, (req2, resp2, webapp2, uri2) -> {
                                        restrictUnloginedUsersFilter.doFilter(req2, resp2, webapp2, uri2, FJUrl.LOGIN, (req3, resp3, webapp3, uri3) -> {
                                            settingsController.doGet(req3, resp3, webapp3, uri3);
                                        });
                                    });
                                });
                            });
                            break;
                        case FJUrl.MESSAGE:
                            exitFilter.doFilter(request, response, webappName, userURI, controllerName, false, (req, resp, webapp, uri) -> {
                                loginFilter.doFilter(req, resp, webapp, uri, FJUrl.VIEW_THREAD, (req1, resp1, webapp1, uri1) -> {
                                    localeResolver.doFilter(req1, resp1, webapp1, uri1, (req2, resp2, webapp2, uri2) -> {
                                        messageController.doGet(req2, resp2, webapp2, uri2);
                                    });
                                });
                            });
                            break;
                        case FJUrl.LOGIN:
                            localeResolver.doFilter(request, response, webappName, userURI, (req2, resp2, webapp2, uri2) -> {
                                loginController.doGet(req2, resp2, webapp2, uri2);
                            });
                            break;
                        case FJUrl.NEW_QUESTION:
                            exitFilter.doFilter(request, response, webappName, userURI, controllerName, true, (req, resp, webapp, uri) -> {
                                loginFilter.doFilter(req, resp, webapp, uri, FJUrl.VIEW_THREAD, (req1, resp1, webapp1, uri1) -> {
                                    localeResolver.doFilter(req1, resp1, webapp1, uri1, (req2, resp2, webapp2, uri2) -> {
                                        restrictUnloginedUsersFilter.doFilter(req2, resp2, webapp2, uri2, FJUrl.LOGIN, (req3, resp3, webapp3, uri3) -> {
                                            newQuestionController.doGet(req3, resp3, webapp3, uri3);
                                        });
                                    });
                                });
                            });
                            break;
                        case FJUrl.REGISTRATION:
                            localeResolver.doFilter(request, response, webappName, userURI, (req2, resp2, webapp2, uri2) -> {
                                registrationController.doGet(req2, resp2, webapp2, uri2);
                            });
                            break;
                        case FJUrl.ACTIVATE_USER:
                            confirmEmailController.doGet(request, response, userURI);
                            break;
                        case FJUrl.ADD_IGNOR:
                            loginFilter.doFilter(request, response, webappName, userURI, controllerName, (req, resp, webapp, uri) -> {
                                restrictUnloginedUsersFilter.doFilter(req, resp, webapp, uri, FJUrl.LOGIN, (req3, resp3, webapp3, uri3) -> {
                                    addIgnorController.doGet(req3, resp3, uri3);
                                });
                            });
                            break;
                        case FJUrl.APPROVE_USER:
                            loginFilter.doFilter(request, response, webappName, userURI, controllerName, (req, resp, webapp, uri) -> {
                                restrictUnloginedUsersFilter.doFilter(req, resp, webapp, uri, FJUrl.LOGIN, (req3, resp3, webapp3, uri3) -> {
                                    approveUserController.doGet(req3, resp3, uri3);
                                });
                            });
                            break;
                        case FJUrl.BAN:
                            loginFilter.doFilter(request, response, webappName, userURI, controllerName, (req, resp, webapp, uri) -> {
                                restrictUnloginedUsersFilter.doFilter(req, resp, webapp, uri, FJUrl.LOGIN, (req3, resp3, webapp3, uri3) -> {
                                    banController.doGet(req3, resp3, uri3);
                                });
                            });
                            break;
                        case FJUrl.CLOSE_THREAD:
                            loginFilter.doFilter(request, response, webappName, userURI, controllerName, (req, resp, webapp, uri) -> {
                                restrictUnloginedUsersFilter.doFilter(req, resp, webapp, uri, FJUrl.LOGIN, (req3, resp3, webapp3, uri3) -> {
                                    closeThreadController.doGet(req3, resp3, uri3);
                                });
                            });
                            break;
                        case FJUrl.MOVE_THREAD_TO_RECYCLE:
                            loginFilter.doFilter(request, response, webappName, userURI, controllerName, (req, resp, webapp, uri) -> {
                                restrictUnloginedUsersFilter.doFilter(req, resp, webapp, uri, FJUrl.LOGIN, (req3, resp3, webapp3, uri3) -> {
                                    moveToRecycleController.doGet(req3, resp3, uri3);
                                });
                            });
                            break;
                        case FJUrl.PIN_THREAD:
                            loginFilter.doFilter(request, response, webappName, userURI, controllerName, (req, resp, webapp, uri) -> {
                                restrictUnloginedUsersFilter.doFilter(req, resp, webapp, uri, FJUrl.LOGIN, (req3, resp3, webapp3, uri3) -> {
                                    pinThreadController.doGet(req3, resp3, uri3);
                                });
                            });
                            break;
                        default:
                            page404.doGet(request, response, webappName);
                            break;
                    }
                }else{
                    page404.doGet(request, response, webappName);
                }
            }else{
                page404.doGet(request, response, webappName);
            }
        } catch (Exception e) {
            e.printStackTrace();
            StringBuffer buffer = new StringBuffer();
            buffer = new StringBuffer();
            buffer.append(errorOut(e));
            response.setContentType("text/html; charset=UTF-8");
            PrintWriter writer = response.getWriter();
            String out = buffer.toString();
            writer.write(out);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            request.setCharacterEncoding("UTF-8");
            String path = request.getRequestURI();
            // removing doubled slashes
            path = path.replace("//", "/");
            if (path != null && !path.isEmpty()) {
                String[] pathParts = path.split("/");
                String userURI = getUserURI(pathParts);
                if (isCorrectUserURI(userURI)) {
                    String controllerName = getControllerName(pathParts);
                    if (!webappName.isEmpty()) {
                        userURI = webappName + "/" + userURI;
                    }
                    switch (controllerName) {
                        case FJUrl.ADD_POST:
                            loginFilter.doFilter(request, response, webappName, userURI, FJUrl.INDEX, (req, resp, webapp, uri) -> {
                                localeResolver.doFilter(req, resp, webapp, uri, (req2, resp2, webapp2, uri2) -> {
                                    restrictUnloginedUsersFilter.doFilter(req2, resp2, webapp2, uri2, FJUrl.LOGIN, (req3, resp3, webapp3, uri3) -> {
                                        addPostController.doPost(req3, resp3, webapp3, uri3);
                                    });
                                });
                            });
                            break;
                        case FJUrl.ADD_THREAD:
                            loginFilter.doFilter(request, response, webappName, userURI, FJUrl.INDEX, (req, resp, webapp, uri) -> {
                                localeResolver.doFilter(req, resp, webapp, uri, (req2, resp2, webapp2, uri2) -> {
                                    restrictUnloginedUsersFilter.doFilter(req2, resp2, webapp2, uri2, FJUrl.LOGIN, (req3, resp3, webapp3, uri3) -> {
                                        addThreadController.doPost(req3, resp3, webapp3, uri3);
                                    });
                                });
                            });
                            break;
                        case FJUrl.DO_LOGIN:
                            doLoginController.doPost(request, response, webappName, userURI);
                            break;
                        case FJUrl.ADD_QUESTION:
                            loginFilter.doFilter(request, response, webappName, userURI, FJUrl.INDEX, (req, resp, webapp, uri) -> {
                                localeResolver.doFilter(req, resp, webapp, uri, (req2, resp2, webapp2, uri2) -> {
                                    restrictUnloginedUsersFilter.doFilter(req2, resp2, webapp2, uri2, FJUrl.LOGIN, (req3, resp3, webapp3, uri3) -> {
                                        addQuestionController.doPost(req3, resp3, webapp3, uri3);
                                    });
                                });
                            });
                            break;
                        case FJUrl.ADD_SUBSCRIBE:
                            loginFilter.doFilter(request, response, webappName, userURI, FJUrl.INDEX, (req, resp, webapp, uri) -> {
                                restrictUnloginedUsersFilter.doFilter(req, resp, webapp, uri, FJUrl.LOGIN, (req3, resp3, webapp3, uri3) -> {
                                    addSubscribeController.doPost(req3, resp3, webapp3, uri3);
                                });
                            });
                            break;
                        case FJUrl.UPDATE_IGNORING:
                            loginFilter.doFilter(request, response, webappName, userURI, FJUrl.INDEX, (req, resp, webapp, uri) -> {
                                restrictUnloginedUsersFilter.doFilter(req, resp, webapp, uri, FJUrl.LOGIN, (req3, resp3, webapp3, uri3) -> {
                                    updateIgnorController.doPost(req3, resp3, webapp3, uri3);
                                });
                            });
                            break;
                        case FJUrl.SET_DEFAULT_VIEW:
                            loginFilter.doFilter(request, response, webappName, userURI, FJUrl.INDEX, (req, resp, webapp, uri) -> {
                                restrictUnloginedUsersFilter.doFilter(req, resp, webapp, uri, FJUrl.LOGIN, (req3, resp3, webapp3, uri3) -> {
                                    setDefaultViewController.doPost(req3, resp3, webapp3, uri3);
                                });
                            });
                            break;
                        case FJUrl.FOLDER_TOOLS:
                            loginFilter.doFilter(request, response, webappName, userURI, FJUrl.INDEX, (req, resp, webapp, uri) -> {
                                restrictUnloginedUsersFilter.doFilter(req, resp, webapp, uri, FJUrl.LOGIN, (req3, resp3, webapp3, uri3) -> {
                                    folderToolsController.doPost(req3, resp3, webapp3, uri3);
                                });
                            });
                            break;
                        case FJUrl.DELETE_MAIL:
                            loginFilter.doFilter(request, response, webappName, userURI, FJUrl.INDEX, (req, resp, webapp, uri) -> {
                                restrictUnloginedUsersFilter.doFilter(req, resp, webapp, uri, FJUrl.LOGIN, (req3, resp3, webapp3, uri3) -> {
                                    deleteMailController.doPost(req3, resp3, webapp3, uri3);
                                });
                            });
                            break;
                        case FJUrl.DELETE_SUBSCRIBE:
                            loginFilter.doFilter(request, response, webappName, userURI, FJUrl.INDEX, (req, resp, webapp, uri) -> {
                                restrictUnloginedUsersFilter.doFilter(req, resp, webapp, uri, FJUrl.LOGIN, (req3, resp3, webapp3, uri3) -> {
                                    deleteOneSubscribeController.doPost(req3, resp3, webapp3, uri3);
                                });
                            });
                            break;
                        case FJUrl.DELETE_ONE_SUBSCRIBE_BY_EMAIL:
                            loginFilter.doFilter(request, response, webappName, userURI, FJUrl.INDEX, (req, resp, webapp, uri) -> {
                                restrictUnloginedUsersFilter.doFilter(req, resp, webapp, uri, FJUrl.LOGIN, (req3, resp3, webapp3, uri3) -> {
                                    deleteOneSubscribeFromMailController.doPost(req3, resp3, webapp3, uri3);
                                });
                            });
                            break;
                        case FJUrl.DELETE_SUBSCRIBES:
                            loginFilter.doFilter(request, response, webappName, userURI, FJUrl.INDEX, (req, resp, webapp, uri) -> {
                                restrictUnloginedUsersFilter.doFilter(req, resp, webapp, uri, FJUrl.LOGIN, (req3, resp3, webapp3, uri3) -> {
                                    deleteSubscribesController.doPost(req3, resp3, webapp3, uri3);
                                });
                            });
                            break;
                        case FJUrl.DELETE_FOLDER_FROM_VIEW:
                            loginFilter.doFilter(request, response, webappName, userURI, FJUrl.INDEX, (req, resp, webapp, uri) -> {
                                restrictUnloginedUsersFilter.doFilter(req, resp, webapp, uri, FJUrl.LOGIN, (req3, resp3, webapp3, uri3) -> {
                                    removeFolderFromViewController.doPost(req3, resp3, webapp3, uri3);
                                });
                            });
                            break;
                        case FJUrl.DELETE_VIEW:
                            loginFilter.doFilter(request, response, webappName, userURI, FJUrl.INDEX, (req, resp, webapp, uri) -> {
                                restrictUnloginedUsersFilter.doFilter(req, resp, webapp, uri, FJUrl.LOGIN, (req3, resp3, webapp3, uri3) -> {
                                    deleteViewController.doPost(req3, resp3, webapp3, uri3);
                                });
                            });
                            break;
                        case FJUrl.DELETE_VOICE:
                            loginFilter.doFilter(request, response, webappName, userURI, FJUrl.INDEX, (req, resp, webapp, uri) -> {
                                restrictUnloginedUsersFilter.doFilter(req, resp, webapp, uri, FJUrl.LOGIN, (req3, resp3, webapp3, uri3) -> {
                                    deleteVoiceController.doPost(req3, resp3, webapp3, uri3);
                                });
                            });
                            break;
                        case FJUrl.DO_REGISTRATION:
                            doRegistrationController.doPost(request, response, webappName, userURI);
                            break;
                        case FJUrl.MOVE_TITLE:
                            loginFilter.doFilter(request, response, webappName, userURI, FJUrl.INDEX, (req, resp, webapp, uri) -> {
                                restrictUnloginedUsersFilter.doFilter(req, resp, webapp, uri, FJUrl.LOGIN, (req3, resp3, webapp3, uri3) -> {
                                    moveThreadController.doPost(req3, resp3, webapp3, uri3);
                                });
                            });
                            break;
                        case FJUrl.NEW_FOLDER:
                            loginFilter.doFilter(request, response, webappName, userURI, FJUrl.INDEX, (req, resp, webapp, uri) -> {
                                restrictUnloginedUsersFilter.doFilter(req, resp, webapp, uri, FJUrl.LOGIN, (req3, resp3, webapp3, uri3) -> {
                                    createFolderController.doPost(req3, resp3, webapp3, uri3);
                                });
                            });
                            break;
                        case FJUrl.NEW_VIEW:
                            loginFilter.doFilter(request, response, webappName, userURI, FJUrl.INDEX, (req, resp, webapp, uri) -> {
                                restrictUnloginedUsersFilter.doFilter(req, resp, webapp, uri, FJUrl.LOGIN, (req3, resp3, webapp3, uri3) -> {
                                    createViewController.doPost(req3, resp3, webapp3, uri3);
                                });
                            });
                            break;
                        case FJUrl.PING:
                            loginFilter.doFilter(request, response, webappName, userURI, FJUrl.INDEX, (req, resp, webapp, uri) -> {
                                pingController.doPost(req, resp, webapp, uri);
                            });
                            break;
                        case FJUrl.POST:
                            loginFilter.doFilter(request, response, webappName, userURI, FJUrl.INDEX, (req, resp, webapp, uri) -> {
                                restrictUnloginedUsersFilter.doFilter(req, resp, webapp, uri, FJUrl.LOGIN, (req3, resp3, webapp3, uri3) -> {
                                    commandHandler.doPost(req3, resp3, webapp3, uri3);
                                });
                            });
                            break;
                        case FJUrl.POST_IMAGE:
                            fileUploadFilter.doFilter(request, response, webappName, userURI, (req, resp, webapp, uri) -> {
                                loginFilter.doFilter(req, resp, webapp, uri, FJUrl.INDEX, (req1, resp1, webapp1, uri1) -> {
                                    restrictUnloginedUsersFilter.doFilter(req1, resp1, webapp1, uri1, FJUrl.LOGIN, (req3, resp3, webapp3, uri3) -> {
                                        addImageController.doPost(req3, resp3, webapp3, uri3);
                                    });
                                });
                            });
                            break;
                        case FJUrl.SEND_PIVATE_MESSAGE:
                            loginFilter.doFilter(request, response, webappName, userURI, FJUrl.INDEX, (req, resp, webapp, uri) -> {
                                localeResolver.doFilter(req, resp, webapp, uri, (req2, resp2, webapp2, uri2) -> {
                                    restrictUnloginedUsersFilter.doFilter(req2, resp2, webapp2, uri2, FJUrl.LOGIN, (req3, resp3, webapp3, uri3) -> {
                                        sendPrivateMessagController.doPost(req3, resp3, webapp3, uri3);
                                    });
                                });
                            });
                            break;
                        case FJUrl.SET_AVATAR:
                            fileUploadFilter.doFilter(request, response, webappName, userURI, (req, resp, webapp, uri) -> {
                                loginFilter.doFilter(req, resp, webapp, uri, FJUrl.INDEX, (req1, resp1, webapp1, uri1) -> {
                                    restrictUnloginedUsersFilter.doFilter(req1, resp1, webapp1, uri1, FJUrl.LOGIN, (req3, resp3, webapp3, uri3) -> {
                                        setAvatarController.doPost(req3, resp3, webapp3, uri3);
                                    });
                                });
                            });
                            break;
                        case FJUrl.SET_FOOTER:
                            loginFilter.doFilter(request, response, webappName, userURI, FJUrl.INDEX, (req, resp, webapp, uri) -> {
                                restrictUnloginedUsersFilter.doFilter(req, resp, webapp, uri, FJUrl.LOGIN, (req3, resp3, webapp3, uri3) -> {
                                    setFooterController.doPost(req3, resp3, webapp3, uri3);
                                });
                            });
                            break;
                        case FJUrl.SET_LOCATION:
                            loginFilter.doFilter(request, response, webappName, userURI, FJUrl.INDEX, (req, resp, webapp, uri) -> {
                                restrictUnloginedUsersFilter.doFilter(req, resp, webapp, uri, FJUrl.LOGIN, (req3, resp3, webapp3, uri3) -> {
                                    setLocationController.doPost(req3, resp3, webapp3, uri3);
                                });
                            });
                            break;
                        case FJUrl.SELECT_VIEW:
                            loginFilter.doFilter(request, response, webappName, userURI, FJUrl.INDEX, (req, resp, webapp, uri) -> {
                                restrictUnloginedUsersFilter.doFilter(req, resp, webapp, uri, FJUrl.LOGIN, (req3, resp3, webapp3, uri3) -> {
                                    changeViewController.doPost(req3, resp3, webapp3, uri3);
                                });
                            });
                            break;
                        case FJUrl.ADD_VOTE:
                            loginFilter.doFilter(request, response, webappName, userURI, FJUrl.INDEX, (req, resp, webapp, uri) -> {
                                restrictUnloginedUsersFilter.doFilter(req, resp, webapp, uri, FJUrl.LOGIN, (req3, resp3, webapp3, uri3) -> {
                                    addCustomAnswerController.doPost(req3, resp3, webapp3, uri3);
                                });
                            });
                            break;
                        case FJUrl.V_AVATAR:
                            loginFilter.doFilter(request, response, webappName, userURI, FJUrl.INDEX, (req, resp, webapp, uri) -> {
                                restrictUnloginedUsersFilter.doFilter(req, resp, webapp, uri, FJUrl.LOGIN, (req3, resp3, webapp3, uri3) -> {
                                    setViewAvatarController.doPost(req3, resp3, webapp3, uri3);
                                });
                            });
                            break;
                        case FJUrl.VOICE:
                            loginFilter.doFilter(request, response, webappName, userURI, FJUrl.INDEX, (req, resp, webapp, uri) -> {
                                restrictUnloginedUsersFilter.doFilter(req, resp, webapp, uri, FJUrl.LOGIN, (req3, resp3, webapp3, uri3) -> {
                                    addVoteController.doPost(req3, resp3, webapp3, uri3);
                                });
                            });
                            break;
                        default:
                            page404.doGet(request, response, webappName);
                            break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            StringBuffer buffer = new StringBuffer();
            buffer = new StringBuffer();
            buffer.append(errorOut(e));
            response.setContentType("text/html; charset=UTF-8");
            PrintWriter writer = response.getWriter();
            String out = buffer.toString();
            writer.write(out);
        }
    }

    private String getUserURI(String[] pathParts){
        int userPosition = 1;
        if (!webappName.isEmpty()){
            userPosition = 2;
        }
        return pathParts[userPosition]; // pathParts[0] is empty
    }

    private String getControllerName(String[] pathParts){
        String controllerName = null;
        int controllerPosition = webappName.isEmpty() ? 2 : 3;
        if (!isStaticResource(pathParts)){
            if (pathParts.length == controllerPosition){
                controllerName = FJUrl.INDEX;
            }else {
                controllerName = pathParts[controllerPosition];
            }
        }else{
            controllerName = pathParts[controllerPosition - 1];
        }
        return controllerName;
    }

    private boolean isStaticResource(String[] pathParts){
        boolean result = false;
        int minimalSize = webappName.isEmpty() ? 2 : 3;
        if (pathParts.length > minimalSize){
            result = pathParts[minimalSize - 1].equals(FJUrl.STATIC);
        }
        return result;
    }

    private boolean isCorrectUserURI(String partPath){
        //TODO it is temporary stub!!
        return partPath.equals("forum") || partPath.equals(FJUrl.STATIC);
    }
}
