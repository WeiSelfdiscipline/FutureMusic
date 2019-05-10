package mapper;



import entity.SongList;

import java.util.List;

/**
 * 对歌单或专辑表操作的Mapper
 *
 * @author 5月9日 张易兴创建
 */
public interface SongListMapper {

    /**
     * 查找符合指定规则的歌单或专辑信息
     *
     * @param songList 按照指定规则查找指定歌单或专辑
     *                 封装信息：
     *                 name按照名字模糊查歌单或专辑
     *                 activity查找参加指定活动的专辑
     *                 userId和type查找指定用户的所有歌单或专辑
     * @return List<SongList>  返回查找到的指定歌单或专辑的信息
     */
    public List<SongList> selectListSongList(SongList songList);

    /**
     * 添加指定歌单或专辑的信息
     *
     * @param songList 歌单或专辑对象
     *                 封装信息：除id以外的所有信息
     * @return int 返回添加的条数
     */
    public int insertSongList(SongList songList);// 添加歌单或专辑
    /**
     * 更新指定歌单或专辑的信息
     *
     * @param songList 歌单或专辑对象 封装所有信息
     * @return int 返回更新的条数
     */
    public int updateSongList(SongList songList);// 更新歌单或专辑
    /**
     * 删除指定歌单或专辑的信息
     *
     * @param id 歌单或专辑的id
     * @return int 返回删除的条数
     */
    public int deleteSongList(int id);
}